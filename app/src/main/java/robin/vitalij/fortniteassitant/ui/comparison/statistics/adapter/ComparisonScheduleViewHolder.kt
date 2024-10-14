package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.HorizontalBarChartBinding.initTwoPlayers
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerScheduleBinding
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsListItem

class ComparisonScheduleViewHolder(
    private val binding: ItemComparisonPlayerScheduleBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ComparisonStatisticsListItem.ScheduleItem) {
        binding.battlesTitle.text = item.title
        binding.killsChart.initTwoPlayers(item)
    }

}