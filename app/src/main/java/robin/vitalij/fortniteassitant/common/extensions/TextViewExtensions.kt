package robin.vitalij.fortniteassitant.common.extensions

import android.widget.TextView
import androidx.core.content.ContextCompat
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.model.enums.TopType


fun TextView.setTopType(topType: TopType) {
    setText(topType.getStringRes())
}

fun TextView.setTextPercent(value: Double) {
    text = value.getStringFormat() + "%"
}

fun TextView.setValueText(value: Double) {
    text = value.getStringFormat()
}

fun TextView.setValueText(value: Int) {
    text = value.getStringFormat()
}

fun TextView.setGameType(gameType: GameType) {
    setText(gameType.getTitleRes())
}

fun TextView.setBattlesType(battlesType: BattlesType) {
    setText(battlesType.getTitleRes())
}

fun TextView.setTypeShop(typeShop: String) {
    setText(ShopType.getShopType(typeShop).getTitleRes())
}

fun TextView.setHoursGame(hoursGame: Double) {
    text =
        if (hoursGame != 0.0) hoursGame.getStringFormat() else context.getString(R.string.no_information)
}

fun TextView.setSessionData(startTimeUpdate: Long, endTimeUpdate: Long) {
    text = "${startTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)} - ${
        endTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)
    }"
}

fun TextView.setDifference(valueComparison: Double) {
    setVisibility(valueComparison != 0.0)
    val color = if (valueComparison > 0) R.color.color_green_elo else R.color.color_red_elo
    setTextColor(ContextCompat.getColor(context, color))
    text = if (valueComparison > 0) "+" + valueComparison.getStringFormat()
    else valueComparison.getStringFormat()
}