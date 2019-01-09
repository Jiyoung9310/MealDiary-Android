package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal interface MainViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toClickDiaryItem(diaryItem: ListItem.DiaryItem)
        fun toNavigateToWrite()
        fun toShowDiaryDialog()
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofClickDiaryItem(): Observable<ListItem.DiaryItem>
        fun ofNavigateToWrite(): Observable<Unit>
        fun ofShowDiaryDialog(): Observable<Unit>
    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val diaryItems: LiveData<List<ListItem.DiaryItem>> = localRepository.diaries().map { diaries ->
            diaries.map { ListItem.DiaryItem(id = it.id.toString(), content = it.content) }
        }.toLiveData()

        private val clickWriteRelay = PublishRelay.create<Unit>()
        private val clickDiaryItemRelay = PublishRelay.create<ListItem.DiaryItem>()
        private val showDiaryDialogRelay = PublishRelay.create<Unit>()
        private val navigateToWriteRelay = PublishRelay.create<Unit>()

        init {
            disposables.addAll(
                    outputs.ofClickWrite()
                            .throttleClick()
                            .subscribeOf(onNext = { toNavigateToWrite() }),

                    outputs.ofClickDiaryItem()
                            .throttleClick()
                            .subscribeOf(onNext = { toShowDiaryDialog() })
            )
        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toClickDiaryItem(diaryItem: ListItem.DiaryItem) = clickDiaryItemRelay.accept(diaryItem)
        override fun toNavigateToWrite() = navigateToWriteRelay.accept(Unit)
        override fun toShowDiaryDialog() = showDiaryDialogRelay.accept(Unit)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofClickDiaryItem(): Observable<ListItem.DiaryItem> = clickDiaryItemRelay
        override fun ofNavigateToWrite(): Observable<Unit> = navigateToWriteRelay
        override fun ofShowDiaryDialog(): Observable<Unit> = showDiaryDialogRelay

    }
}