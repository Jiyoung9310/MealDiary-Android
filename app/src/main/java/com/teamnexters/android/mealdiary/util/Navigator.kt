package com.teamnexters.android.mealdiary.util

import android.content.Context
import android.content.Intent
import com.teamnexters.android.mealdiary.ui.write.WriteActivity

internal class Navigator {
    companion object {
        fun navigateToWrite(context: Context?) {
            context?.startActivity(Intent(context, WriteActivity::class.java))
        }
    }
}