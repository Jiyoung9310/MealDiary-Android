package com.teamnexters.android.mealdiary.base

import android.content.Intent
import android.os.Bundle
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.RequestCode
import com.teamnexters.android.mealdiary.util.ResultCode

internal sealed class LifecycleState {
    data class OnCreate(val screen: Screen) : LifecycleState()

    object OnStart : LifecycleState()

    object OnResume : LifecycleState()

    object OnPause : LifecycleState()

    object OnStop : LifecycleState()

    object OnDestroy : LifecycleState()

    class OnActivityResult(val requestCode: RequestCode, val resultCode: ResultCode, val data: Intent?) : LifecycleState()
}