package robin.vitalij.fortniteassitant.common.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FULL = "dd MMM yyyy HH:mm:ss"

const val DATE_STATS_FULL = "dd.MM.yyyy HH:mm:ss"

//2020-11-06T23:55:37+00:00
@SuppressLint("SimpleDateFormat")
fun String.getDateFull(datePattern: String): String {
    val outputFormat = SimpleDateFormat(datePattern)
    val date = getFormatterDate(this.dropLast(6))
    return outputFormat.format(date)
}

//2020-07-26T22:49:53Z
@SuppressLint("SimpleDateFormat")
fun String.getDateZFull(datePattern: String): String {
    val outputFormat = SimpleDateFormat(datePattern)
    val date = getFormatterDate(this.dropLast(1))
    return outputFormat.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getFormatterDate(dateTitle: String): Date {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val inputFormat2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    return try {
        inputFormat.parse(dateTitle)
    } catch (ex: Exception) {
        try {
            inputFormat2.parse(dateTitle)
        } catch (ex: Exception) {
            Date(0)
        }
    }
}