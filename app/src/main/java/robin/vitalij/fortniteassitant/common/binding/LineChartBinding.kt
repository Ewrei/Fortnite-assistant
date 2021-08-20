package robin.vitalij.fortniteassitant.common.binding

import android.graphics.DashPathEffect
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.utils.MyXAxisValueFormatter
import robin.vitalij.fortniteassitant.utils.view.MyMarkerView
import java.util.*

object LineChartBinding {

    @JvmStatic
    @BindingAdapter("session")
    fun LineChart.setSession(sessionModels: List<SessionModel>) {
        setDrawGridBackground(false)
        description.isEnabled = false

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(false)
        axisLeft.isEnabled = false
        axisRight.textColor =
            ContextCompat.getColor(context, android.R.color.white)
        xAxis.textColor =
            ContextCompat.getColor(context, android.R.color.white)

        animateX(1000)

        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = this // For bounds control
        marker = mv


        setChart(sessionModels, this)
    }

    private fun setChart(sessionModels: List<SessionModel>, lineChart: LineChart) {

        val values = ArrayList<Entry>()

        for (i in sessionModels.indices) {
            values.add(Entry(i.toFloat(), sessionModels[i].value.toFloat()))
        }

        val dataSet = LineDataSet(values, "")
        dataSet.color = ContextCompat.getColor(lineChart.context, android.R.color.white)
        dataSet.valueTextColor = ContextCompat.getColor(lineChart.context, android.R.color.white)
        dataSet.setCircleColor(ContextCompat.getColor(lineChart.context, android.R.color.white))

        dataSet.lineWidth = 1f
        dataSet.circleRadius = 3f
        dataSet.setDrawCircleHole(false)
        dataSet.valueTextSize = 9f
        dataSet.setDrawFilled(true)
        dataSet.formLineWidth = 1f
        dataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        dataSet.formSize = 15f

        dataSet.setDrawFilled(true)
        dataSet.fillDrawable =
            ContextCompat.getDrawable(lineChart.context, R.drawable.gradient_line_chart)

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = MyXAxisValueFormatter(sessionModels)
        xAxis.granularity = 1f; // minimum axis-step (interval) is 1

        // set data
        // Setting Data
        val data = LineData(dataSet)
        lineChart.data = data

        val sets = lineChart.data.dataSets

        for (iSet in sets) {
            val set = iSet as LineDataSet
            set.setDrawValues(!set.isDrawValuesEnabled)
        }


        lineChart.legend.isEnabled = false
        lineChart.setScaleEnabled(false)
        lineChart.invalidate()
    }
}