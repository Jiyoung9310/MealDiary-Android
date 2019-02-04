package com.teamnexters.android.mealdiary.ui.write.note

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

internal interface NoteViewModel {
    interface Inputs {
        fun toTitle(title: String)
        fun toHashTag(tag: String)
        fun toContent(content: String)
        fun toClickNext()
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofTitle(): Observable<String>
        fun ofHashTag(): Observable<String>
        fun ofContent(): Observable<String>
        fun ofClickNext(): Observable<Unit>
        fun ofNavigate(): Observable<Screen>
    }

    class ViewModel(
            private val schedulerProvider: SchedulerProvider

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        val title = MutableLiveData<String>()
        val hashTag = MutableLiveData<String>()
        val content = MutableLiveData<String>()

        val nextEnable = MutableLiveData<Boolean>().apply { postValue(false) }

        private val titleRelay = BehaviorRelay.createDefault("")
        private val hashTagRelay = BehaviorRelay.createDefault("")
        private val contentRelay = BehaviorRelay.createDefault("")

        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateRelay = PublishRelay.create<Screen>()

        init {
            disposables.addAll(
                    outputs.ofTitle()
                            .subscribeOf(onNext = {
                                nextEnable.postValue(it.isNotBlank())
                                title.postValue(it)
                            }),

                    outputs.ofHashTag()
                            .subscribeOf(onNext = { hashTag.postValue(it) }),

                    outputs.ofContent()
                            .subscribeOf(onNext = { content.postValue(it) }),

                    outputs.ofClickNext()
                            .withLatestFrom(ofScreen<Screen.Write.Note>(), outputs.ofTitle(), outputs.ofContent(), outputs.ofHashTag()) { _, screen, title, content, hashTags ->
                                screen.writeParam.apply {
                                    this.title = title
                                    this.content = content
                                    this.hashTags = listOf(hashTags)
                                }
                            }
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Photo(it)) })
            )
        }

        override fun toTitle(title: String) = titleRelay.accept(title)
        override fun ofTitle(): Observable<String> = titleRelay

        override fun toHashTag(tag: String) = hashTagRelay.accept(tag)
        override fun ofHashTag(): Observable<String> = hashTagRelay

        override fun toContent(content: String) = contentRelay.accept(content)
        override fun ofContent(): Observable<String> = contentRelay

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}