package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
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
    private val showDiaryDialogRelay = PublishRelay.create<ListItem.DiaryItem>()
    private val navigateToWriteRelay = PublishRelay.create<Unit>()

    private val clickModifyRelay = PublishRelay.create<String>()
    private val navigateToModifyRelay = PublishRelay.create<String>()
    private val clickDeleteDiaryItemRelay = PublishRelay.create<ListItem.DiaryItem>()

    init {
        disposables.addAll(
                ofClickWrite()
                        .throttleClick()
                        .subscribeOf(onNext = { toNavigateToWrite() }),

                ofClickModify()
                        .throttleClick()
                        .subscribeOf(onNext = { toNavigateToModify(it) }),

                ofClickDiaryItem()
                        .throttleClick()
                        .subscribeOf(onNext = { toShowDiaryDialog(it) }),

                ofClickDeleteDiaryItem()
                        .throttleClick()
                        .map { Diary(it.id.toInt(), it.content) }
                        .doOnNext { localRepository.deleteDiaries(it).subscribeOf() }
                        .subscribeOf(onComplete = { })
        )
    }

    fun toClickWrite() = clickWriteRelay.accept(Unit)
    fun ofClickWrite(): Observable<Unit> = clickWriteRelay

    fun toClickModify(text: String) = clickModifyRelay.accept(text)
    fun ofClickModify(): Observable<String> = clickModifyRelay

    fun toClickDiaryItem(diaryItem: ListItem.DiaryItem) = clickDiaryItemRelay.accept(diaryItem)
    fun ofClickDiaryItem(): Observable<ListItem.DiaryItem> = clickDiaryItemRelay

    fun toClickDeleteDiaryItem(diaryItem: ListItem.DiaryItem) = clickDeleteDiaryItemRelay.accept(diaryItem)
    fun ofClickDeleteDiaryItem(): Observable<ListItem.DiaryItem> = clickDeleteDiaryItemRelay

    fun toNavigateToWrite() = navigateToWriteRelay.accept(Unit)
    fun ofNavigateToWrite(): Observable<Unit> = navigateToWriteRelay

    fun toNavigateToModify(text: String) = navigateToModifyRelay.accept(text)
    fun ofNavigateToModify(): Observable<String> = navigateToModifyRelay

    fun toShowDiaryDialog(diaryItem: ListItem.DiaryItem) = showDiaryDialogRelay.accept(diaryItem)
    fun ofShowDiaryDialog(): Observable<ListItem.DiaryItem> = showDiaryDialogRelay

}