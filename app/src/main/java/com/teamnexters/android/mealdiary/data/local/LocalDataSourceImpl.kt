package com.teamnexters.android.mealdiary.data.local

import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable

internal class LocalDataSourceImpl(
        private val roomDatabase: MealDiaryRoomDatabase,
        private val schedulerProvider: SchedulerProvider

) : LocalDataSource {

    override fun diaries(): Flowable<List<Diary>> {
        return roomDatabase.diaryDao().get()
    }

    override fun diary(id: Long): Flowable<Diary> {
        return roomDatabase.diaryDao().getById(id)
    }

    override fun deleteDiary(id: Long): Completable {
        return transaction {
            roomDatabase.diaryDao().deleteById(id)
        }
    }

    override fun upsertDiaries(vararg diaries: Diary): Completable {
        return transaction {
            roomDatabase.diaryDao().upserts(*diaries)
            roomDatabase.restaurantDao().upserts(*diaries.map { it.restaurant }.toTypedArray())
            roomDatabase.hashTagDao().upserts(*diaries.flatMap { it.hashTags }.distinct().toTypedArray())
        }
    }

    override fun deleteDiaries(vararg diaries: Diary): Completable {
        return transaction {
            roomDatabase.diaryDao().deletes(*diaries)
        }
    }

    override fun deleteAllDiaries(): Completable {
        return transaction {
            roomDatabase.diaryDao().deleteAll()
        }
    }

    private fun transaction(action: () -> Unit): Completable {
        return Completable.fromAction {
            action()
        }.subscribeOn(schedulerProvider.io())
    }

}