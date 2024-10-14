package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerHeaderBinding
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsListItem

class ComparisonStatisticsHeaderViewHolder(
    private val binding: ItemComparisonPlayerHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ComparisonStatisticsListItem.HeaderItem) {
        binding.title.text = item.title
    }

}