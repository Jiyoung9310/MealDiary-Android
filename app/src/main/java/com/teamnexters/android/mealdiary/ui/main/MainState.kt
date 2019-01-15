package com.teamnexters.android.mealdiary.ui.main

internal sealed class MainState {
    data class ShowDiaryDialog(val message: CharSequence, val positiveButtonText: CharSequence, val negativeButtonText: CharSequence) : MainState()
}