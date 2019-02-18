package com.teamnexters.android.mealdiary.util

import com.teamnexters.android.mealdiary.data.local.entity.HashTag

internal class HashTagUtil {
    companion object {
        @JvmStatic
        fun toHashTagList(value: String): List<HashTag> {
            return value.replace("\\s".toRegex(), "#").split('#').filter { it.isNotBlank() }.map { HashTag(tagName = it) }
        }

        @JvmStatic
        fun toString(hashTags: List<HashTag>): String {

            val stringBuilder = StringBuilder()

            hashTags.forEachIndexed { index, hashTag ->
                if(index != 0) {
                    stringBuilder.append(" ")
                }

                stringBuilder.append("#${hashTag.tagName}")
            }

            return stringBuilder.toString()
        }
    }
}
