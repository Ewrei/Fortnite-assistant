package robin.vitalij.fortniteassitant.common.extensions

import android.content.res.Resources

fun Resources.dpToPx(dp: Int): Int = (dp * displayMetrics.density + 0.5f).toInt()

fun Resources.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = getDimensionPixelSize(resourceId)
    }
    return result
}