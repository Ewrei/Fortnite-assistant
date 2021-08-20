package robin.vitalij.fortniteassitant.common.binding

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.comparison.ManyPlayerSchedule
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonScheduleViewModel
import robin.vitalij.fortniteassitant.utils.XAxisValueFormatter

private const val TWO_PLAYERS = 2

object HorizontalBarChartBinding {

    @JvmStatic
    @BindingAdapter("manyPlayerSchedules")
    fun HorizontalBarChart.setManyPlayerSchedules(manyPlayerSchedules: List<ManyPlayerSchedule>) {

        if (manyPlayerSchedules.size > TWO_PLAYERS) {
            this.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                100 * manyPlayerSchedules.size
            )
        }

        setScaleEnabled(false)
        setDrawBarShadow(false)
        description.isEnabled = false
        setPinchZoom(false)
        setDrawGridBackground(false)

        val yr = axisRight
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f
        yr.textColor = ContextCompat.getColor(context, R.color.text_color)
        setDrawBarShadow(false)
        legend.isEnabled = false

        setDrawValueAboveBar(true)
        setDrawGridBackground(false)
        setFitBars(true)
        animateY(2500)
        setDrawGridBackground(false)

        val xAxis = xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.textColor = ContextCompat.getColor(context, R.color.text_color)

        val yLeft = axisLeft
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        val values = arrayListOf<String>()
        manyPlayerSchedules.forEach {
            values.add(it.accountName)
        }

        xAxis.valueFormatter = XAxisValueFormatter(values.toTypedArray())

        val cd = BarData(getBarEntities(manyPlayerSchedules, context))
        cd.barWidth = 0.9f

        data = cd
        invalidate()
    }

    private fun getBarEntities(
        manyPlayerSchedules: List<ManyPlayerSchedule>,
        context: Context
    ): ArrayList<IBarDataSet> {
        val sets = ArrayList<IBarDataSet>()

        for (i in manyPlayerSchedules.indices) {
            val entries = ArrayList<BarEntry>()
            entries.add(BarEntry(i.toFloat(), manyPlayerSchedules[i].value.toFloat()))

            val barDataSetOnePlayer = BarDataSet(entries, manyPlayerSchedules[i].accountName)
            barDataSetOnePlayer.color = ContextCompat.getColor(context, R.color.text_color)
            barDataSetOnePlayer.barShadowColor =
                ContextCompat.getColor(context, R.color.text_color)
            barDataSetOnePlayer.valueTextSize =
                context.resources.getInteger(R.integer.infografika_size).toFloat()
            barDataSetOnePlayer.valueTextColor = ContextCompat.getColor(context, R.color.text_color)
            sets.add(barDataSetOnePlayer)
        }
        return sets
    }

    fun HorizontalBarChart.initTwoPlayers(comparisonScheduleViewModel: ComparisonScheduleViewModel) {
        setScaleEnabled(false)
        setDrawBarShadow(false)
        description.isEnabled = false
        setPinchZoom(false)
        setDrawGridBackground(false)

        val yr = axisRight
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f
        yr.textColor = ContextCompat.getColor(context, R.color.text_color)
        setDrawBarShadow(false)
        isEnabled = false

        setDrawValueAboveBar(true)
        setDrawGridBackground(false)
        setFitBars(true)
        animateY(2500)
        setDrawGridBackground(false)

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, comparisonScheduleViewModel.value.toFloat()))

        val xAxis = xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.textColor = ContextCompat.getColor(context, R.color.text_color)

        val yLeft = axisLeft
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        val values =
            arrayOf(comparisonScheduleViewModel.nickName, comparisonScheduleViewModel.nickNameTwo)
        xAxis.valueFormatter = XAxisValueFormatter(values)

        val barDataSetOnePlayer = BarDataSet(entries, comparisonScheduleViewModel.nickName)
        barDataSetOnePlayer.color = ContextCompat.getColor(context, R.color.color_orange)
        barDataSetOnePlayer.barShadowColor = ContextCompat.getColor(context, R.color.color_orange)
        barDataSetOnePlayer.valueTextSize =
            context.resources.getInteger(R.integer.infografika_size).toFloat()
        barDataSetOnePlayer.valueTextColor =
            ContextCompat.getColor(context, R.color.text_color)

        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(1f, comparisonScheduleViewModel.valueTwo.toFloat()))

        val barDataSetTwoPlayer = BarDataSet(entries2, comparisonScheduleViewModel.nickNameTwo)
        barDataSetTwoPlayer.color =
            ContextCompat.getColor(context, R.color.telegram)
        barDataSetTwoPlayer.barShadowColor =
            ContextCompat.getColor(context, R.color.telegram)
        barDataSetTwoPlayer.valueTextSize =
            context.resources.getInteger(R.integer.infografika_size).toFloat()
        barDataSetTwoPlayer.valueTextColor =
            ContextCompat.getColor(context, R.color.text_color)

        val sets = ArrayList<IBarDataSet>()
        sets.add(barDataSetOnePlayer)
        sets.add(barDataSetTwoPlayer)

        val cd = BarData(sets)
        cd.barWidth = 0.9f

        legend.isEnabled = false
        data = cd
        invalidate()
    }
}