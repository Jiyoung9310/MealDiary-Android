package com.teamnexters.android.mealdiary.ui.write

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable

internal class WriteViewModel(
        schedulerProvider: SchedulerProvider,
        localRepository: LocalRepository

) : BaseViewModel() {

    val content = MutableLiveData<String>()

    private val clickWriteRelay = PublishRelay.create<Unit>()
    private val navigateToMainRelay = PublishRelay.create<Unit>()

    private val contentRelay = BehaviorRelay.create<String>()

    init {
        disposables.addAll(
                ofContent()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = content::setValue),

                ofClickWrite()
                        .throttleClick()
                        .withLatestFromSecond(ofContent())
                        .map { Diary(content = it) }
                        .doOnNext { localRepository.upsertDiaries(it).subscribeOf() }
                        .subscribeOf(onComplete = { toNavigateToMain() })
        )
    }

    fun toClickWrite() = clickWriteRelay.accept(Unit)
    fun ofClickWrite(): Observable<Unit> = clickWriteRelay

    fun toContent(content: String) = contentRelay.accept(content)
    fun ofContent(): Observable<String> = contentRelay

    fun toNavigateToMain() = navigateToMainRelay.accept(Unit)
    fun ofNavigateToMain(): Observable<Unit> = navigateToMainRelay

}