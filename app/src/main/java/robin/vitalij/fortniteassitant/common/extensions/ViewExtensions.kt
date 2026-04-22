package robin.vitalij.fortniteassitant.common.extensions

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

fun View.applySystemBarsPadding() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.setPadding(0, statusBarHeight, 0, navBarHeight)
            insets
        }

        ViewCompat.requestApplyInsets(this)
    }
}