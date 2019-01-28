package com.teamnexters.android.mealdiary.ui

import com.jakewharton.rxrelay2.BehaviorRelay

internal interface ToolbarChannel {
    fun toolbarRelay(): BehaviorRelay<ToolbarResources>
}