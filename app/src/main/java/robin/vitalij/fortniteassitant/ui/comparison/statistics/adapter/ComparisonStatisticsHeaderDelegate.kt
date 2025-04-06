package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerHeaderBinding
import robin.vitalij.fortniteassitant.utils.AdapterDelegate

class ComparisonStatisticsHeaderDelegate : AdapterDelegate<ComparisonStatisticsListItem> {
    override fun isForViewType(item: ComparisonStatisticsListItem) =
        item is ComparisonStatisticsListItem.HeaderItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemComparisonPlayerHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ComparisonStatisticsListItem
    ) {
        (holder as HeaderViewHolder).bind(item as ComparisonStatisticsListItem.HeaderItem)
    }

    class HeaderViewHolder(
        private val binding: ItemComparisonPlayerHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComparisonStatisticsListItem.HeaderItem) {
            binding.title.text = item.title
        }
    }
}