package com.teamnexters.android.mealdiary.data.local.converter

import androidx.room.TypeConverter
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

internal class ZonedDateTimeConverter {
    companion object {
        val ISO8601: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.KOREA)

        private val ZONE_SEOUL = ZoneId.of("Asia/Seoul")

        private val days = listOf("월", "화", "수", "목", "금", "토", "일")

        fun weekDay(date: ZonedDateTime): String {
            return days[date.dayOfWeek.value - 1]
        }
    }

    @TypeConverter
    fun fromDate(value: ZonedDateTime): String {
        return ISO8601.format(value)
    }

    @TypeConverter
    fun toDate(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value, ISO8601).withZoneSameInstant(ZONE_SEOUL)
    }

}