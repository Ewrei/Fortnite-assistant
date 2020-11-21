package robin.vitalij.fortniteassitant.utils

import java.math.BigDecimal
import java.math.RoundingMode

private const val ZERO_VALUE = 0.0
private const val NEW_SCALE = 2
private const val ONE_HUNDRED = 100

object TextUtils {

    fun getAveragePercent(wins: Double?, battles: Double?): Double {
        return if (wins == null || battles == null || wins == ZERO_VALUE || battles == ZERO_VALUE) {
            ZERO_VALUE
        } else {
            BigDecimal(wins / battles * ONE_HUNDRED).setScale(NEW_SCALE, RoundingMode.UP).toDouble()
        }
    }

    fun getAverage(wins: Double?, battles: Double?): Double {
        return if (wins == null || battles == null || wins == ZERO_VALUE || battles == ZERO_VALUE) {
            ZERO_VALUE
        } else {
            BigDecimal(wins / battles).setScale(NEW_SCALE, RoundingMode.UP).toDouble()
        }
    }
}