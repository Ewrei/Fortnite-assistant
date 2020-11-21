package robin.vitalij.fortniteassitant.common.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat

object TextViewBinding {

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("percent")
    fun TextView.setTextPercent(value: Double) {
        text = value.getStringFormat() + "%"
    }

    @JvmStatic
    @BindingAdapter("value")
    fun TextView.setValueText(value: Double) {
        text = value.getStringFormat()
    }

    @JvmStatic
    @BindingAdapter("value")
    fun TextView.setValueText(value: Int) {
        text = value.getStringFormat()
    }
}