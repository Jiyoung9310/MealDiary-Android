package com.teamnexters.android.mealdiary.ui.main

import com.teamnexters.android.mealdiary.base.BaseViewModel
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.repository.LocalRepository
import com.teamnexters.android.mealdiary.util.extension.toLiveData

internal class MainViewModel(private val localRepository: LocalRepository) : BaseViewModel() {
    val diaryItems = localRepository.diaries().map { diaries ->
        diaries.map { ListItem.DiaryItem(id = it.id.toString(), content = it.content) }
    }.toLiveData()

    init {
        localRepository.upsertDiaries(listOf(
                Diary(content = "asd"),
                Diary(content = "dsa")
        )).subscribe()
    }

}