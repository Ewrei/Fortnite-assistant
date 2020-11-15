package robin.vitalij.fortniteassitant.common.extensions

import android.view.View

fun View.setVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

fun View.setInVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
}