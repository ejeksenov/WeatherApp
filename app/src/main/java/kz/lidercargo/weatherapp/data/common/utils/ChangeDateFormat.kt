package kz.lidercargo.weatherapp.data.common.utils

import java.text.SimpleDateFormat
import java.util.*

object ChangeDateFormat {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.ENGLISH)

    fun changeDateFormatToDate(dateTime: String): String {
        return try {
            val outputFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
            val date: Date = inputFormat.parse(dateTime) as Date
            outputFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun changeDateFormatToTime(dateTime: String): String {
        return try {
            val outputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val date: Date = inputFormat.parse(dateTime) as Date
            outputFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}