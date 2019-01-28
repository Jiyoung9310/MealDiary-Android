package com.teamnexters.android.mealdiary.ui

import androidx.annotation.DrawableRes
import com.teamnexters.android.mealdiary.R

internal sealed class ToolbarResources(@DrawableRes val navigationRes: Int) {
    class None: ToolbarResources(R.drawable.ic_arrow_back_material)

    class Photo : ToolbarResources(R.drawable.ic_arrow_back_material)
    class Restaurant : ToolbarResources(R.drawable.ic_arrow_back_material)
    class Write : ToolbarResources(R.drawable.ic_arrow_back_material)
    class Score : ToolbarResources(R.drawable.ic_close)
}