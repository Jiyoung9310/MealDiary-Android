package com.teamnexters.android.mealdiary.ui

internal class ToolbarResourcesProviderImpl : ToolbarResourcesProvider {
    override fun get(screen: Screen): ToolbarResources {
        return when(screen) {
            is Screen.Write.Note -> ToolbarResources.Note()
            is Screen.Write.Photo -> ToolbarResources.Photo()
            is Screen.Write.Score -> ToolbarResources.Score()
            else -> ToolbarResources.None()
        }
    }
}