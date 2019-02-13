package com.teamnexters.android.mealdiary.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teamnexters.android.mealdiary.data.local.entity.Diary
import com.teamnexters.android.mealdiary.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

internal class LocalDataSourceImpl(
        private val roomDatabase: MealDiaryRoomDatabase,
        private val sharedPreferences: SharedPreferences,
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
            diaries.map { it.restaurant }.forEach { restaurant ->
                restaurant?.let {  }
            }

            diaries.forEach {
                it.restaurant?.let { restaurant ->  roomDatabase.restaurantDao().upserts(restaurant) }
            }
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

    override fun setPrivacyAgree(value: Boolean): Completable {
        return transaction {
            sharedPreferences.edit { putBoolean("privacyAgree", value) }
        }
    }

    override fun getPrivacyAgree(): Observable<Boolean> {
        return Observable.just(sharedPreferences.getBoolean("privacyAgree", false))
    }
}