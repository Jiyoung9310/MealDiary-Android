package com.teamnexters.android.mealdiary.ui

import java.io.Serializable

//TODO @Parcelize가 sealed 클래스에 적용되면 바꿔야함
internal sealed class Screen : Serializable {
    object Main : Screen()

    sealed class Write : Screen() {
        object Photo : Screen.Write()
        object Restaurant : Screen.Write()
        object Write : Screen.Write()
        object Score : Screen.Write()
    }
}