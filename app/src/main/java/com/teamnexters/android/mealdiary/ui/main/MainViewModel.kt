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

internal class MainViewModel(
        schedulerProvider: SchedulerProvider,
        localRepository: LocalRepository
) : BaseViewModel() {

    val diaryItems: LiveData<List<ListItem.DiaryItem>> = localRepository.diaries().map { diaries ->
        diaries.map { ListItem.DiaryItem(id = it.id.toString(), content = it.content) }
    }.toLiveData()

    private val clickWriteRelay = PublishRelay.create<Unit>()
    private val clickDiaryItemRelay = PublishRelay.create<ListItem.DiaryItem>()
    private val showDiaryDialogRelay = PublishRelay.create<String>()
    private val navigateToWriteRelay = PublishRelay.create<Unit>()

    init {
        disposables.addAll(
                ofClickWrite()
                        .throttleClick()
                        .subscribeOf(onNext = { toNavigateToWrite() }),

                ofClickDiaryItem()
                        .throttleClick()
                        .subscribeOf(onNext = { toShowDiaryDialog(it.content) })
        )
    }

    fun toClickWrite() = clickWriteRelay.accept(Unit)
    fun ofClickWrite(): Observable<Unit> = clickWriteRelay

    fun toClickDiaryItem(diaryItem: ListItem.DiaryItem) = clickDiaryItemRelay.accept(diaryItem)
    fun ofClickDiaryItem(): Observable<ListItem.DiaryItem> = clickDiaryItemRelay

    fun toNavigateToWrite() = navigateToWriteRelay.accept(Unit)
    fun ofNavigateToWrite(): Observable<Unit> = navigateToWriteRelay

    fun toShowDiaryDialog(item: String) = showDiaryDialogRelay.accept(item)
    fun ofShowDiaryDialog(): Observable<String> = showDiaryDialogRelay

}