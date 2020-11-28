package robin.vitalij.fortniteassitant.common.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_SHORT_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
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

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("startTimeUpdate", "endTimeUpdate")
    fun TextView.setSessionData(startTimeUpdate: Long, endTimeUpdate: Long) {
        text = "${startTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)} - ${
            endTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)
        }"
    }

    @JvmStatic
    @BindingAdapter("chartsType")
    fun TextView.setChartsType(chartsType: ChartsType) {
        setText(chartsType.getTitleRes())
    }

    @JvmStatic
    @BindingAdapter("difference")
    fun TextView.setDifference(valueComparison: Double) {
        setVisibility(valueComparison != 0.0)
        val color = if (valueComparison > 0) R.color.color_green_elo else R.color.color_red_elo
        setTextColor(ContextCompat.getColor(context, color))
        text = if (valueComparison > 0) "+" + valueComparison.getStringFormat()
        else valueComparison.getStringFormat()
    }

    @JvmStatic
    @BindingAdapter("difference")
    fun setDifferenceText(textView: TextView, value: String) {
        try {
            val result = java.lang.Double.parseDouble(value)
            val color =
                if (result > 0.0) R.color.color_green_elo else if (result < 0.0) R.color.color_red_elo else R.color.text_color
            textView.setTextColor(ContextCompat.getColor(textView.context, color))

            textView.text =
                if (result > 0) "+" + result.getStringFormat() else result.getStringFormat()

        } catch (e: NumberFormatException) {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.text_color))
            textView.text = value
        }
    }

    @JvmStatic
    @BindingAdapter("differencePercent")
    fun setDifferencePercentText(textView: TextView, value: String) {
        try {
            val result = java.lang.Double.parseDouble(value)
            val color =
                if (result > 0.0) R.color.color_green_elo else if (result < 0.0) R.color.color_red_elo else R.color.text_color
            textView.setTextColor(ContextCompat.getColor(textView.context, color))

            textView.text =
                if (result > 0) "+" + result.getStringFormat() + "%" else result.getStringFormat() + "%"

        } catch (e: NumberFormatException) {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.text_color))
            textView.text = value
        }
    }
}