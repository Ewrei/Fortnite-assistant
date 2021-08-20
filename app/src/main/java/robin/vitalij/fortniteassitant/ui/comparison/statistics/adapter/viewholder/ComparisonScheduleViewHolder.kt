package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder

import kotlinx.android.synthetic.main.item_comparison_player_schedule.view.*
import robin.vitalij.fortniteassitant.common.binding.HorizontalBarChartBinding.initTwoPlayers
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerScheduleBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonScheduleViewModel

class ComparisonScheduleViewHolder(override val binding: ItemComparisonPlayerScheduleBinding) :
    BaseViewHolder<ComparisonPlayer>(binding) {

    override fun bind(item: ComparisonPlayer) {
        if (item is ComparisonScheduleViewModel) {
            binding.item = item
            itemView.killsChart.initTwoPlayers(item)
        }
    }
}