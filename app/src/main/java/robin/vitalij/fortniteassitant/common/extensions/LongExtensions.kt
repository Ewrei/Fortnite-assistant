package robin.vitalij.fortniteassitant.common.extensions

import java.text.NumberFormat

fun Long?.getStringFormat(): String =
    this?.let {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = MAXIMUM_FRACTION_DIGITS
        numberFormat.isGroupingUsed = true
        numberFormat.format(this)
    } ?: "-"