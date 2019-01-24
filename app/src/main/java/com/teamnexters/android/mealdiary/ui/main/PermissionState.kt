package com.teamnexters.android.mealdiary.ui.main

internal sealed class PermissionState {
    object Granted: PermissionState()

    object Denied : PermissionState()
}