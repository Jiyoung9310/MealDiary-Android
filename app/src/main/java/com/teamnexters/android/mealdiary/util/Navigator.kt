package com.teamnexters.android.mealdiary.util

import android.content.Context
import android.content.Intent
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.ui.detail.DetailActivity
import com.teamnexters.android.mealdiary.ui.write.WriteActivity
import com.teamnexters.android.mealdiary.util.extension.screen

internal class Navigator {
    companion object {
        @JvmStatic
        fun navigateToWrite(context: Context?, screen: Screen) {
            context?.startActivity(
                    Intent(context, WriteActivity::class.java).screen(screen)
            )
        }
        @JvmStatic
        fun navigateToDetail(context: Context?, screen: Screen) {
            context?.startActivity(Intent(context, DetailActivity::class.java).screen(screen))
        }
    }
}