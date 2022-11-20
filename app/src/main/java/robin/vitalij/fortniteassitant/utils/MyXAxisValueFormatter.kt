package robin.vitalij.fortniteassitant.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.model.SessionModel

class MyXAxisValueFormatter(private val values: List<SessionModel>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase) =
        values[value.toInt()].timestamp.getDateStringFormat(DATE_PATTERN)
}