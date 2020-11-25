package robin.vitalij.fortniteassitant.common.extensions

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN_YEAR_TIME = "dd MMMM yyyy HH:mm"
const val DATE_PATTERN = "dd.MM"

fun Int?.getStringFormat(): String =
    this?.let {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = MAXIMUM_FRACTION_DIGITS
        numberFormat.isGroupingUsed = true
        numberFormat.format(this)
    } ?: "-"

@SuppressLint("SimpleDateFormat")
fun Long.getDateStringFormat(datePattern: String, isUnix: Boolean = false): String {
    val unixSeconds = java.lang.Long.valueOf(this)
    val date =
        Date(unixSeconds * if (isUnix) 1000 else 1) // *1000 is to convert seconds to milliseconds
    val formatDate = SimpleDateFormat(datePattern)
    return formatDate.format(date)
}