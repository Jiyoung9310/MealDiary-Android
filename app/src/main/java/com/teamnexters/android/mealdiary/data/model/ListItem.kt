package com.teamnexters.android.mealdiary.data.model

internal sealed class ListItem(val listItemId: String) {

    class DiaryItem(val id: Long, val content: String) : ListItem("$id$content")

}