package com.teamnexters.android.mealdiary.ui.write

import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import com.teamnexters.android.mealdiary.data.local.entity.Restaurant
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

internal class WriteParam : Serializable {
    var content: String? = null
    var score: Int? = null
    var photoUrls: List<String> = mutableListOf()

    var restaurant: Restaurant? = null
    var hashTags: List<HashTag> = mutableListOf()

    var date: ZonedDateTime? = null
    var id: Long = -1
}