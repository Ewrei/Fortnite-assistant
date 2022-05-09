package robin.vitalij.fortniteassitant.common.extensions

import android.view.View
import android.view.ViewGroup
import robin.vitalij.fortniteassitant.utils.SafeClickListener

fun View.setVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

fun View.setInVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener {
        onSafeClick(it)
    })
}

fun View.setMarginTop(marginTop: Int, marginButton: Int = 0, marginLeft: Int = 0, marginRight: Int = 0) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginButton)
    this.layoutParams = menuLayoutParams
}