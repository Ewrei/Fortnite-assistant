package robin.vitalij.fortniteassitant.common.extensions

import android.widget.Button

fun Button.setClickableButton(isClick: Boolean) {
    isEnabled = isClick
    isClickable = isClick
}