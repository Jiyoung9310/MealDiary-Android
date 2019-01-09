package com.teamnexters.android.mealdiary.ui

import java.io.Serializable

//TODO @Parcelize가 sealed 클래스에 적용되면 바꿔야함
internal sealed class Screen : Serializable {
    object Main : Screen()

    class Write(val id: String? = null) : Screen()
}