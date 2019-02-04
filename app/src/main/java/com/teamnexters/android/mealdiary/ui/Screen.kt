package com.teamnexters.android.mealdiary.ui

import com.teamnexters.android.mealdiary.ui.write.WriteParam
import java.io.Serializable

//TODO @Parcelize가 sealed 클래스에 적용되면 바꿔야함
internal sealed class Screen : Serializable {
    object Main : Screen()

    sealed class Write(val writeParam: WriteParam) : Screen() {
        class Photo(writeParam: WriteParam) : Screen.Write(writeParam)
        class Restaurant(writeParam: WriteParam) : Screen.Write(writeParam)
        class Note(writeParam: WriteParam) : Screen.Write(writeParam)
        class Score(writeParam: WriteParam) : Screen.Write(writeParam)
    }
}