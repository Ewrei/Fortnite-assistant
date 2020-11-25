package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonStatisticsHeaderViewModel

class ComparisonStatisticsHeaderViewHolder(override val binding: ItemComparisonPlayerHeaderBinding) :
    BaseViewHolder<ComparisonPlayer>(binding) {

    override fun bind(item: ComparisonPlayer) {
        if (item is ComparisonStatisticsHeaderViewModel) {
            binding.item = item
        }
    }
}