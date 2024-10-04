package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder

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
                    binding.differenceOne.setDifference(difference)
                    binding.differenceOne.setInVisibility(true)
                    binding.differenceTwo.setInVisibility(false)
                }

                difference < 0.0 -> {
                    binding.differenceTwo.setDifference(difference * -1)
                    binding.differenceOne.setInVisibility(false)
                    binding.differenceTwo.setInVisibility(true)
                }

                else -> {
                    binding.differenceOne.setVisibility(false)
                    binding.differenceTwo.setVisibility(false)
                }
            }
        }
    }
}