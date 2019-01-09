package com.teamnexters.android.mealdiary.util.extension

import android.content.Intent
import android.os.Bundle
import com.teamnexters.android.mealdiary.MealDiaryConst
import com.teamnexters.android.mealdiary.ui.Screen

internal fun Intent.screen(screen: Screen): Intent {
    return this.apply {
        putExtra(MealDiaryConst.KEY_ARGS, Bundle().apply {
            putSerializable(MealDiaryConst.KEY_ARGS, screen)
        })
    }
}