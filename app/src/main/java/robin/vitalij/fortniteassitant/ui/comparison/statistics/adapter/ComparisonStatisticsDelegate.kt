package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setDifference
import robin.vitalij.fortniteassitant.common.extensions.setInVisibility
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerStatisticsBinding
import robin.vitalij.fortniteassitant.utils.AdapterDelegate

class ComparisonStatisticsDelegate : AdapterDelegate<ComparisonStatisticsListItem> {

    override fun isForViewType(item: ComparisonStatisticsListItem) =
        item is ComparisonStatisticsListItem.StatisticsItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemComparisonPlayerStatisticsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ComparisonStatisticsListItem
    ) {
        (holder as StatisticsViewHolder).bind(item as ComparisonStatisticsListItem.StatisticsItem)
    }

    class StatisticsViewHolder(
        private val binding: ItemComparisonPlayerStatisticsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComparisonStatisticsListItem.StatisticsItem) {
            binding.title.text = item.title
            binding.valueFirst.setValueText(item.value)
            binding.valueSecond.setValueText(item.valueTwo)

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