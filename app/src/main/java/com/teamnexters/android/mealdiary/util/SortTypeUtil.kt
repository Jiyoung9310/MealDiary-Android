package com.teamnexters.android.mealdiary.util

import com.teamnexters.android.mealdiary.R

class SortTypeUtil {
    enum class SortType(val type: Int, val stringId: Int) {
        LATEST(0, R.string.sort_latest),
        SCORE(1, R.string.sort_score),
        DISTANCE(2, R.string.sort_distance);
    }
    fun getSortString(type: Int): Int {
        return when(type) {
            SortType.LATEST.type -> SortType.LATEST.stringId
            SortType.SCORE.type -> SortType.SCORE.stringId
            SortType.DISTANCE.type -> SortType.DISTANCE.stringId
            else -> 0
        }
    }
}