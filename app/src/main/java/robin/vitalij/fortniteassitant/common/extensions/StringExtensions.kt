package robin.vitalij.fortniteassitant.common.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FULL = "dd MMM yyyy HH:mm:ss"

fun String.getDateFull(): String {
    val outputFormat = SimpleDateFormat(DATE_FULL)
    val date = getFormatterDate(this.dropLast(6))
    return outputFormat.format(date)
}

//2020-11-06T23:55:37+00:00

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