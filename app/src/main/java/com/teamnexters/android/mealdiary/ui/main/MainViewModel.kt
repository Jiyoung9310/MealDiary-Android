package com.teamnexters.android.mealdiary.ui.main

import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.ui.write.WriteParam
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import com.teamnexters.android.mealdiary.util.extension.toLiveData
import com.teamnexters.android.mealdiary.util.extension.withLatestFromSecond
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import org.threeten.bp.ZonedDateTime


internal interface MainViewModel {
    interface Inputs {
        fun toClickWrite()
        fun toClickModify()
        fun toClickDelete()
        fun toClickDiaryItem(diary: Diary)
        fun toShowDiaryDialog(state: MainState.ShowDiaryDialog)
        fun toNavigate(screen: Screen)
        fun toClickedDiary(diary: Diary)
        fun toPermissionState(permissionState: PermissionState)
    }

    interface Outputs {
        fun ofClickWrite(): Observable<Unit>
        fun ofClickModify(): Observable<Unit>
        fun ofClickDelete(): Observable<Unit>
        fun ofClickDiaryItem(): Observable<Diary>
        fun ofShowDiaryDialog(): Observable<MainState.ShowDiaryDialog>
        fun ofNavigate(): Observable<Screen>
        fun ofClickedDiary(): Observable<Diary>
        fun ofPermissionState(): Observable<PermissionState>
    }

    class ViewModel(
            schedulerProvider: SchedulerProvider,
            localRepository: LocalRepository

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

//        val photoList = mutableListOf("https://travelblog.expedia.co.kr/wp-content/uploads/2015/07/34.png")
//        val rest = Restaurant("브라운동까스", "강남구 어디로 123번길")
//        val hash = mutableListOf(HashTag("#브돈 #디캠 #맛집"))
//
//        val diaryItems: LiveData<List<ListItem.DiaryItem>> = Observable.just(listOf(
//                Diary(content = "식후감 쓰는 부분쓰", score = 60, photoUrls = photoList, restaurant =  rest, hashTags = hash, date = ZonedDateTime.now())
//        )).map { diaries ->
//            diaries.map { ListItem.DiaryItem(it) }
//        }.toLiveData()

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
                                                message = it.content ?: "",
                                                positiveButtonText = "삭제",
                                                negativeButtonText = "수정"
                                        )
                                )
                            }),

                    outputs.ofClickDelete()
                            .throttleClick()
                            .withLatestFromSecond(outputs.ofClickedDiary())
                            .subscribeOf(onNext = {
                                localRepository.deleteDiary(it)
                                        .subscribeOf()
                            }),

                    outputs.ofPermissionState()
                            .ofType<PermissionState.Granted>()
                            .subscribeOf(onNext = { inputs.toNavigate(Screen.Write.Restaurant(WriteParam())) })
            )
        }

        override fun toClickWrite() = clickWriteRelay.accept(Unit)
        override fun toClickModify() = clickModifyDiaryItemRelay.accept(Unit)
        override fun toClickDelete() = clickDeleteDiaryItemRelay.accept(Unit)
        override fun toClickDiaryItem(diary: Diary) = clickDiaryItemRelay.accept(diary)
        override fun toNavigate(screen: Screen) = navigateToWriteRelay.accept(screen)
        override fun toShowDiaryDialog(state: MainState.ShowDiaryDialog) = showDiaryDialogRelay.accept(state)
        override fun toClickedDiary(diary: Diary) = clickedDiaryRelay.accept(diary)
        override fun toPermissionState(permissionState: PermissionState) = permissionStateRelay.accept(permissionState)

        override fun ofClickWrite(): Observable<Unit> = clickWriteRelay
        override fun ofClickModify(): Observable<Unit> = clickModifyDiaryItemRelay
        override fun ofClickDelete(): Observable<Unit> = clickDeleteDiaryItemRelay
        override fun ofClickDiaryItem(): Observable<Diary> = clickDiaryItemRelay
        override fun ofShowDiaryDialog(): Observable<MainState.ShowDiaryDialog> = showDiaryDialogRelay
        override fun ofNavigate(): Observable<Screen> = navigateToWriteRelay
        override fun ofClickedDiary(): Observable<Diary> = clickedDiaryRelay
        override fun ofPermissionState(): Observable<PermissionState> = permissionStateRelay
    }
}