package com.teamnexters.android.mealdiary.data.model

import com.teamnexters.android.mealdiary.data.local.entity.Diary

internal sealed class ListItem(val listItemId: String) {

    class DiaryItem(val diary: Diary) : ListItem(diary.toString())

}