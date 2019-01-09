package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable


internal interface MainViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toClickDiaryItem(diaryItem: ListItem.DiaryItem)
        fun toNavigateToWrite(screen: Screen)
        fun toShowDiaryDialog(diaryItem: ListItem.DiaryItem)
        fun toClickModify(id: Long)
        fun toClickDelete(id: Long)
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofClickDiaryItem(): Observable<ListItem.DiaryItem>
        fun ofNavigateToWrite(): Observable<Screen>
        fun ofShowDiaryDialog(): Observable<ListItem.DiaryItem>
        fun ofClickModify(): Observable<Long>
        fun ofClickDelete(): Observable<Long>
    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diaryItems: LiveData<List<ListItem.DiaryItem>> = localRepository.diaries().map { diaries ->
            diaries.map { ListItem.DiaryItem(id = it.id, content = it.content) }
        }.toLiveData()

        private val clickWriteRelay = PublishRelay.create<Unit>()
        private val clickDiaryItemRelay = PublishRelay.create<ListItem.DiaryItem>()
        private val showDiaryDialogRelay = PublishRelay.create<ListItem.DiaryItem>()
        private val navigateToWriteRelay = PublishRelay.create<Screen>()
        private val clickModifyDiaryItemRelay = PublishRelay.create<Long>()
        private val clickDeleteDiaryItemRelay = PublishRelay.create<Long>()

        init {
            disposables.addAll(
                    outputs.ofClickWrite()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                inputs.toNavigateToWrite(Screen.Write.Write)
                            }),

                    outputs.ofClickDiaryItem()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                inputs.toShowDiaryDialog(it)
                            }),

                    outputs.ofClickModify()
                            .throttleClick()
                            .subscribeOf(onNext = { id ->
                                inputs.toNavigateToWrite(Screen.Write.Modify(id))
                            }),

                    outputs.ofClickDelete()
                            .throttleClick()
                            .subscribeOf(onNext = {
                                localRepository.deleteDiary(it)
                                        .subscribeOf()
                            })
            )

        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toClickDiaryItem(diaryItem: ListItem.DiaryItem) = clickDiaryItemRelay.accept(diaryItem)
        override fun toNavigateToWrite(screen: Screen) = navigateToWriteRelay.accept(screen)
        override fun toShowDiaryDialog(diaryItem: ListItem.DiaryItem) = showDiaryDialogRelay.accept(diaryItem)
        override fun toClickModify(id: Long) = clickModifyDiaryItemRelay.accept(id)
        override fun toClickDelete(id: Long) = clickDeleteDiaryItemRelay.accept(id)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofClickDiaryItem(): Observable<ListItem.DiaryItem> = clickDiaryItemRelay
        override fun ofNavigateToWrite(): Observable<Screen> = navigateToWriteRelay
        override fun ofShowDiaryDialog(): Observable<ListItem.DiaryItem> = showDiaryDialogRelay
        override fun ofClickModify(): Observable<Long> = clickModifyDiaryItemRelay
        override fun ofClickDelete(): Observable<Long> = clickDeleteDiaryItemRelay
    }
}