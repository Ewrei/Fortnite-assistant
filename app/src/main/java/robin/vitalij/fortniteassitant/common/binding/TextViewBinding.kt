package robin.vitalij.fortniteassitant.common.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.model.enums.ChartsType

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

    @JvmStatic
    @BindingAdapter("hoursGame")
    fun TextView.setHoursGame(hoursGame: Double) {
        text =
            if (hoursGame != 0.0) hoursGame.getStringFormat() else context.getString(R.string.no_information)
    }

    @JvmStatic
    @BindingAdapter("chartsType")
    fun TextView.setChartsType(chartsType: ChartsType) {
        setText(chartsType.getTitleRes())
    }
}