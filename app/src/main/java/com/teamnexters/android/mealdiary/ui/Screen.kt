package com.teamnexters.android.mealdiary.ui

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import java.io.Serializable

//TODO @Parcelize가 sealed 클래스에 적용되면 바꿔야함
internal sealed class Screen : Serializable {
    object Main : Screen()

    sealed class Write : Screen() {
        object Write : Screen.Write()

        class Modify(val diaryId: Long) : Screen.Write()
    }
}