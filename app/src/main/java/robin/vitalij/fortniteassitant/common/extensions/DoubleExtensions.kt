package robin.vitalij.fortniteassitant.common.extensions

import java.text.NumberFormat

const val MAXIMUM_FRACTION_DIGITS = 2

fun Double?.getStringFormat(): String =
    this?.let {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = MAXIMUM_FRACTION_DIGITS
        numberFormat.isGroupingUsed = true
        numberFormat.format(this)
    } ?: "-"

fun Double?.getStringFormat(format: String): String =
    this?.let {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = MAXIMUM_FRACTION_DIGITS
        numberFormat.isGroupingUsed = true
        numberFormat.format(this) + "$format"
    } ?: "-"
