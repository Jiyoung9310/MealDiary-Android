package com.teamnexters.android.mealdiary.ui

import java.io.Serializable

//TODO @Parcelize가 sealed 클래스에 적용되면 바꿔야함
internal sealed class Screen : Serializable {
    object Main : Screen()

    sealed class Write : Screen() {
        object Photo : Screen.Write()
        class Restaurant(val photos: List<com.teamnexters.android.mealdiary.ui.write.photo.Photo>) : Screen.Write()
        class Write : Screen.Write()
        class Score : Screen.Write()
    }
}