package robin.vitalij.fortniteassitant.common.extensions

import android.graphics.Color
import android.view.View
import android.view.Window

fun Window.setFullScreen(isFullScreen: Boolean) {
    if (isFullScreen) {
        this.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.statusBarColor = Color.TRANSPARENT
    } else {
        this.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        this.statusBarColor = Color.TRANSPARENT
    }
}