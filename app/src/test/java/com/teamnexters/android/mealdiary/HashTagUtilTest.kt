package com.teamnexters.android.mealdiary

import com.teamnexters.android.mealdiary.data.local.entity.HashTag
import com.teamnexters.android.mealdiary.util.HashTagUtil
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HashTagUtilTest {

    companion object {
        private const val TAG1 = "대전"
        private const val TAG2 = "배네딕트"
        private const val TAG3 = "카이스트"

        private const val HASH_TAG = "#$TAG1 #$TAG2 #$TAG3"

        private val HASH_TAG_LIST = listOf(HashTag(tagName = TAG1), HashTag(tagName = TAG2), HashTag(tagName = TAG3))
    }

    @Test
    fun to_tag_test() {
        val tags = HashTagUtil.toHashTagList(HASH_TAG)

        assertEquals(tags[0].tagName, TAG1)
        assertEquals(tags[1].tagName, TAG2)
        assertEquals(tags[2].tagName, TAG3)
    }

    @Test
    fun to_string_text() {
        assertEquals(HashTagUtil.toString(HASH_TAG_LIST), HASH_TAG)
    }

}