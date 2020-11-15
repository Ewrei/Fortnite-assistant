package robin.vitalij.fortniteassitant.utils.view

import android.content.Context
import android.graphics.Point
import android.text.TextUtils
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import robin.vitalij.fortniteassitant.R

class CenteredTitleToolbar : Toolbar {
    private lateinit var titleTextView: TextView
    private var screenWidth = 0
    private var centerTitle = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        screenWidth = screenSize.x
        titleTextView = TextView(context)
        titleTextView.setTextAppearance(context, R.style.ToolbarTitleText)
        titleTextView.maxLines = 1
        titleTextView.ellipsize = TextUtils.TruncateAt.END
        addView(titleTextView)
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        if (centerTitle) {
            val location = IntArray(2)
            titleTextView.getLocationOnScreen(location)
            titleTextView.translationX =
                titleTextView.translationX + (-location[0] + screenWidth / 2 - titleTextView.width / 2)
        }
    }

    override fun setTitle(title: CharSequence) {
        titleTextView.text = title
        requestLayout()
    }

    override fun setTitle(titleRes: Int) {
        titleTextView.setText(titleRes)
        requestLayout()
    }

    fun setTitleCentered(centered: Boolean) {
        centerTitle = centered
        requestLayout()
    }

    private val screenSize: Point
        get() {
            val wm =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val screenSize = Point()
            display.getSize(screenSize)
            return screenSize
        }
}