package com.teamnexters.android.mealdiary.ui.write.note

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.TextChangedParam
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

internal interface NoteViewModel {
    interface Inputs {
        fun toTitle(title: String)
        fun toHashTagTextParam(s: CharSequence, start: Int, before: Int, count: Int)
        fun toHashTagFocused(hasFocus: Boolean)
        fun toContent(content: String)
        fun toClickNext()
        fun toNavigate(screen: Screen)
    }

    interface Outputs {
        fun ofTitle(): Observable<String>
        fun ofHashTagTextParam(): Observable<TextChangedParam>
        fun ofHashTag(): Observable<String>
        fun ofHashTagFocused(): Observable<Boolean>
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
        private val hashTagRelay = BehaviorRelay.createDefault(TextChangedParam("", 0, 0, 0))
        private val contentRelay = BehaviorRelay.createDefault("")

        private val hashTagFocusedRelay = BehaviorRelay.createDefault(false)
        private val clickNextRelay = PublishRelay.create<Unit>()
        private val navigateRelay = PublishRelay.create<Screen>()

        init {
            disposables.addAll(
                    outputs.ofTitle()
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = {
                                nextEnable.value = it.isNotBlank()
                                title.value = it
                            }),

                    outputs.ofHashTagTextParam()
                            .withLatestFrom(outputs.ofHashTagFocused())
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = { (textParam, focused) ->
                                val stringBuilder = StringBuilder(textParam.s.toString())

                                if(stringBuilder.length >= 2 && stringBuilder.last().isWhitespace() && stringBuilder[stringBuilder.length - 2] == '#') {
                                    stringBuilder.deleteCharAt(stringBuilder.length - 1)
                                } else if(focused && (stringBuilder.isEmpty() || stringBuilder.last().isWhitespace() && textParam.before < textParam.count)) {
                                    stringBuilder.append("#")
                                }

                                hashTag.value = stringBuilder.toString()
                            }),

                    outputs.ofHashTagFocused()
                            .filter { it }
                            .withLatestFromSecond(outputs.ofHashTag())
                            .filter { it.isEmpty() }
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = { hashTag.value = "#" }),

                    outputs.ofContent()
                            .observeOn(schedulerProvider.ui())
                            .subscribeOf(onNext = { content.value = it }),

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

        override fun toHashTagTextParam(s: CharSequence, start: Int, before: Int, count: Int) = hashTagRelay.accept(TextChangedParam(s, start, before, count))
        override fun ofHashTagTextParam(): Observable<TextChangedParam> = hashTagRelay
        override fun ofHashTag(): Observable<String> = hashTagRelay.map { it.s.toString() }

        override fun toHashTagFocused(hasFocus: Boolean) = hashTagFocusedRelay.accept(hasFocus)
        override fun ofHashTagFocused(): Observable<Boolean> = hashTagFocusedRelay

        override fun toContent(content: String) = contentRelay.accept(content)
        override fun ofContent(): Observable<String> = contentRelay

        override fun toClickNext() = clickNextRelay.accept(Unit)
        override fun ofClickNext(): Observable<Unit> = clickNextRelay

        override fun toNavigate(screen: Screen) = navigateRelay.accept(screen)
        override fun ofNavigate(): Observable<Screen> = navigateRelay
    }
}