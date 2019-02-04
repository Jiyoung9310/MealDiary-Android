package com.teamnexters.android.mealdiary.ui

internal interface ToolbarResourcesProvider {
    fun get(screen: Screen): ToolbarResources
}