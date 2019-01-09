package com.teamnexters.android.mealdiary.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.teamnexters.android.mealdiary.ui.write.WriteActivity

internal class Navigator {
    companion object {
        @JvmStatic
        fun navigateToWrite(context: Context?, text: String?) {
            val intent = Intent(context, WriteActivity::class.java)
            intent.putExtra("text", text)
            context?.startActivity(intent)
        }
    }
}