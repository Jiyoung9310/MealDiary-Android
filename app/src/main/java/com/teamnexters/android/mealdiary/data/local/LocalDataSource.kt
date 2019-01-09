package com.teamnexters.android.mealdiary.data.local

import com.teamnexters.android.mealdiary.data.DataSource
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable

internal class LocalDataSource(
        private val roomDatabase: MealDiaryRoomDatabase,
        private val schedulerProvider: SchedulerProvider

) : DataSource {

    override fun diaries(): Flowable<List<Diary>> {
        return roomDatabase.diaryDao().diaries()
    }

    override fun diary(id: Long): Flowable<Diary> {
        return roomDatabase.diaryDao().diary(id)
    }

    override fun deleteDiary(id: Long): Completable {
        return transaction {
            roomDatabase.diaryDao().deleteDiary(id)
        }
    }

    override fun upsertDiaries(vararg diaries: Diary): Completable {
        return transaction {
            roomDatabase.diaryDao().upsertDiaries(*diaries)
        }
    }

    override fun deleteDiaries(vararg diaries: Diary): Completable {
        return transaction {
            roomDatabase.diaryDao().deleteDiaries(*diaries)
        }
    }

    override fun deleteAllDiaries(): Completable {
        return transaction {
            roomDatabase.diaryDao().deleteAllDiaries()
        }
    }

    private fun transaction(action: () -> Unit): Completable {
        return Completable.fromAction {
            action()
        }.subscribeOn(schedulerProvider.io())
    }

}