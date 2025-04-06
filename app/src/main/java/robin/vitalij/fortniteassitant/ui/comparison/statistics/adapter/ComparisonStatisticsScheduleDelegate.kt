package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.HorizontalBarChartBinding.initTwoPlayers
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerScheduleBinding
import robin.vitalij.fortniteassitant.utils.AdapterDelegate

class ComparisonStatisticsScheduleDelegate : AdapterDelegate<ComparisonStatisticsListItem> {

    override fun isForViewType(item: ComparisonStatisticsListItem): Boolean {
        return item is ComparisonStatisticsListItem.ScheduleItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemComparisonPlayerScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ComparisonStatisticsListItem
    ) {
        (holder as ScheduleViewHolder).bind(item as ComparisonStatisticsListItem.ScheduleItem)
    }

    class ScheduleViewHolder(
        private val binding: ItemComparisonPlayerScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComparisonStatisticsListItem.ScheduleItem) {
            binding.battlesTitle.text = item.title
            binding.killsChart.initTwoPlayers(item)
        }
    }
}