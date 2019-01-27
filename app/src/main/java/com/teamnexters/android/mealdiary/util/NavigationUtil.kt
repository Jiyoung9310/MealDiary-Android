package com.teamnexters.android.mealdiary.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

internal class NavigationUtil {
    companion object {
        @JvmStatic
        fun navigateToSettingActivity(context: Context) {
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))

                context.startActivity(intent)

            } catch(e: ActivityNotFoundException) {
                e.printStackTrace()

                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                context.startActivity(intent)
            }
        }
    }
}