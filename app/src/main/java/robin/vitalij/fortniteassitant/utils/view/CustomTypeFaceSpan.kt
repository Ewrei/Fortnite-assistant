package robin.vitalij.fortniteassitant.utils.view

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan
import androidx.annotation.ColorInt

class CustomTypeFaceSpan(
    family: String?, private val newType: Typeface, @get:ColorInt
    @param:ColorInt val foregroundColor: Int
) :
    TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = foregroundColor
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    override fun getSpanTypeId(): Int {
        return super.getSpanTypeId()
    }

    companion object {
        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old: Typeface = paint.getTypeface()
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.setFakeBoldText(true)
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.setTextSkewX(-0.25f)
            }
            paint.setTypeface(tf)
        }
    }
}