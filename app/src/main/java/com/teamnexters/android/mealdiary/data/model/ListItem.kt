package com.teamnexters.android.mealdiary.data.model

internal sealed class ListItem(val id: String) {

    class DiaryItem(id: String, val content: String) : ListItem(id)

}