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
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.model.enums.TopType

object TextViewBinding {

    @JvmStatic
    @BindingAdapter("topType")
    fun TextView.setTopType(topType: TopType) {
        setText(topType.getStringRes())
    }

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
    @BindingAdapter("gameType")
    fun TextView.setGameType(gameType: GameType) {
        setText(gameType.getTitleRes())
    }

    @JvmStatic
    @BindingAdapter("battlesType")
    fun TextView.setBattlesType(battlesType: BattlesType) {
        setText(battlesType.getTitleRes())
    }

    @JvmStatic
    @BindingAdapter("typeShop")
    fun TextView.setTypeShop(typeShop: String) {
        setText(ShopType.getShopType(typeShop).getTitleRes())
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
    @BindingAdapter("difference")
    fun TextView.setDifference(valueComparison: Double) {
        setVisibility(valueComparison != 0.0)
        val color = if (valueComparison > 0) R.color.color_green_elo else R.color.color_red_elo
        setTextColor(ContextCompat.getColor(context, color))
        text = if (valueComparison > 0) "+" + valueComparison.getStringFormat()
        else valueComparison.getStringFormat()
    }
}