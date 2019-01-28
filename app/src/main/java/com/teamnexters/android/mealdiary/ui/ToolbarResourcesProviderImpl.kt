package com.teamnexters.android.mealdiary.ui

internal class ToolbarResourcesProviderImpl : ToolbarResourcesProvider {
    override fun get(screen: Screen): ToolbarResources {
        return when(screen) {
            is Screen.Write.Photo -> ToolbarResources.Photo()
            is Screen.Write.Restaurant -> ToolbarResources.Restaurant()
            is Screen.Write.Write -> ToolbarResources.Write()
            is Screen.Write.Score -> ToolbarResources.Score()
            else -> ToolbarResources.None()
        }
    }
}