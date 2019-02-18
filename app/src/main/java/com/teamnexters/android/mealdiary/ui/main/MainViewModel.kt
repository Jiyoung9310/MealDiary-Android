package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.ui.write.WriteParam
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType


internal interface MainViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toClickDiaryItem(diary: Diary)
        fun toNavigate(screen: Screen)
        fun toPermissionState(permissionState: PermissionState)
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofClickDiaryItem(): Observable<Diary>
        fun ofNavigate(): Observable<Screen>
        fun ofPermissionState(): Observable<PermissionState>
    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diaryItems: LiveData<List<ListItem.DiaryItem>> = localRepository.diaries().map { diaries ->
            diaries.map { ListItem.DiaryItem(it) }
        }.toLiveData()


        private val clickDiaryItemRelay = PublishRelay.create<Diary>()
        private val clickWriteRelay = PublishRelay.create<Unit>()
        private val navigateToWriteRelay = PublishRelay.create<Screen>()
        private val permissionStateRelay = PublishRelay.create<PermissionState>()

        init {
            disposables.addAll(
                    outputs.ofClickDiaryItem()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                inputs.toNavigate(Screen.Detail(it.id))
                            }),

                    outputs.ofPermissionState()
                            .ofType<PermissionState.Granted>()
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Note(WriteParam())) })
            )
        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toClickDiaryItem(diary: Diary) = clickDiaryItemRelay.accept(diary)
        override fun toNavigate(screen: Screen) = navigateToWriteRelay.accept(screen)
        override fun toPermissionState(permissionState: PermissionState) = permissionStateRelay.accept(permissionState)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofClickDiaryItem(): Observable<Diary> = clickDiaryItemRelay
        override fun ofNavigate(): Observable<Screen> = navigateToWriteRelay
        override fun ofPermissionState(): Observable<PermissionState> = permissionStateRelay
    }
}