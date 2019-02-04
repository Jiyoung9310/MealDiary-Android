package com.teamnexters.android.mealdiary.ui

import com.jakewharton.rxrelay2.BehaviorRelay

internal class ToolbarChannelImpl : ToolbarChannel {

    private val toolbarRelay = BehaviorRelay.createDefault<ToolbarResources>(ToolbarResources.None())

    override fun toolbarRelay(): BehaviorRelay<ToolbarResources> {
        return toolbarRelay
    }
}