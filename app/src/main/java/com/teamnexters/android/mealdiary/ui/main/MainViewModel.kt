package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.*
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable


internal interface MainViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toClickModify()
        fun toClickDelete()
        fun toClickDiaryItem(diary: Diary)
        fun toShowDiaryDialog(state: MainState.ShowDiaryDialog)
        fun toNavigateToWrite(screen: Screen)
        fun toClickedDiary(diary: Diary)
        fun toPermissionState(permissionState: PermissionState)
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofClickModify(): Observable<Unit>
        fun ofClickDelete(): Observable<Unit>
        fun ofClickDiaryItem(): Observable<Diary>
        fun ofShowDiaryDialog(): Observable<MainState.ShowDiaryDialog>
        fun ofNavigateToWrite(): Observable<Screen>
        fun ofClickedDiary(): Observable<Diary>
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

        private val clickedDiaryRelay = BehaviorRelay.create<Diary>()

        private val clickDiaryItemRelay = PublishRelay.create<Diary>()
        private val clickModifyDiaryItemRelay = PublishRelay.create<Unit>()
        private val clickDeleteDiaryItemRelay = PublishRelay.create<Unit>()
        private val clickWriteRelay = PublishRelay.create<Unit>()
        private val showDiaryDialogRelay = PublishRelay.create<MainState.ShowDiaryDialog>()
        private val navigateToWriteRelay = PublishRelay.create<Screen>()
        private val permissionStateRelay = PublishRelay.create<PermissionState>()

        init {
            disposables.addAll(
                    outputs.ofClickDiaryItem()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                inputs.toClickedDiary(it)

                                inputs.toShowDiaryDialog(
                                        MainState.ShowDiaryDialog(
                                                message = it.content,
                                                positiveButtonText = "삭제",
                                                negativeButtonText = "수정"
                                        )
                                )
                            }),

                    outputs.ofClickModify()
                            .throttleClick()
                            .withLatestFromSecond(outputs.ofClickedDiary())
                            .subscribeOf(onNext = { inputs.toNavigateToWrite(Screen.Write.Modify(it.id)) }),

                    outputs.ofClickDelete()
                            .throttleClick()
                            .withLatestFromSecond(outputs.ofClickedDiary())
                            .subscribeOf(onNext = {
                                localRepository.deleteDiary(it)
                                        .subscribeOf()
                            })
            )

        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toClickModify() = clickModifyDiaryItemRelay.accept(Unit)
        override fun toClickDelete() = clickDeleteDiaryItemRelay.accept(Unit)
        override fun toClickDiaryItem(diary: Diary) = clickDiaryItemRelay.accept(diary)
        override fun toNavigateToWrite(screen: Screen) = navigateToWriteRelay.accept(screen)
        override fun toShowDiaryDialog(state: MainState.ShowDiaryDialog) = showDiaryDialogRelay.accept(state)
        override fun toClickedDiary(diary: Diary) = clickedDiaryRelay.accept(diary)
        override fun toPermissionState(permissionState: PermissionState) = permissionStateRelay.accept(permissionState)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofClickModify(): Observable<Unit> = clickModifyDiaryItemRelay
        override fun ofClickDelete(): Observable<Unit> = clickDeleteDiaryItemRelay
        override fun ofClickDiaryItem(): Observable<Diary> = clickDiaryItemRelay
        override fun ofShowDiaryDialog(): Observable<MainState.ShowDiaryDialog> = showDiaryDialogRelay
        override fun ofNavigateToWrite(): Observable<Screen> = navigateToWriteRelay
        override fun ofClickedDiary(): Observable<Diary> = clickedDiaryRelay
        override fun ofPermissionState(): Observable<PermissionState> = permissionStateRelay
    }
}