package robin.vitalij.fortniteassitant.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class XAxisValueFormatter(private val values: Array<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        return try {
            this.values[value.toInt()]
        } catch (arrayIndexOutOfBoundsException: ArrayIndexOutOfBoundsException) {
            " "
        }
    }
}