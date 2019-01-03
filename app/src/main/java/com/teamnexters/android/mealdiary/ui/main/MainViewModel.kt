package com.teamnexters.android.mealdiary.ui.main

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

    val diaryItems = localRepository.diaries().map { diaries ->
        diaries.map { ListItem.DiaryItem(id = it.id.toString(), content = it.content) }
    }.toLiveData()

    private val clickWriteRelay = PublishRelay.create<Unit>()
    private val navigateToWriteRelay = PublishRelay.create<Unit>()

    init {
        disposables.addAll(
                ofClickWrite()
                        .throttleClick()
                        .subscribeOf(onNext = { toNavigateToWrite() })
        )
    }

    fun toClickWrite() = clickWriteRelay.accept(Unit)
    fun ofClickWrite(): Observable<Unit> = clickWriteRelay

    fun toNavigateToWrite() = navigateToWriteRelay.accept(Unit)
    fun ofNavigateToWrite(): Observable<Unit> = navigateToWriteRelay

}