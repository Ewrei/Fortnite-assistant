package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder

import kotlinx.android.synthetic.main.item_comparison_player_statistics.view.*
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setDifference
import robin.vitalij.fortniteassitant.common.extensions.setInVisibility
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerStatisticsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonStatisticsViewModel

class ComparisonStatisticsViewHolder(override val binding: ItemComparisonPlayerStatisticsBinding) :
    BaseViewHolder<ComparisonPlayer>(binding) {

    override fun bind(item: ComparisonPlayer) {
        if (item is ComparisonStatisticsViewModel) {
            binding.item = item

            val difference: Double = item.value - item.valueTwo

            when {
                difference > 0.0 -> {
                    itemView.differenceOne.setDifference(difference)
                    itemView.differenceOne.setInVisibility(true)
                    itemView.differenceTwo.setInVisibility(false)
                }
                difference < 0.0 -> {
                    itemView.differenceTwo.setDifference(difference * -1)
                    itemView.differenceOne.setInVisibility(false)
                    itemView.differenceTwo.setInVisibility(true)
                }
                else -> {
                    itemView.differenceOne.setVisibility(false)
                    itemView.differenceTwo.setVisibility(false)
                }
            }
        }
    }
}