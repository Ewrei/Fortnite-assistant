package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyHeaderBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyHeaderViewHolder(private val binding: ItemHomeBodyHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.HeaderItem) {
        binding.title.text = item.title
    }
}