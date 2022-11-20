package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsEmptyBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyEmptyViewHolder(private val binding: ItemBodyStatsEmptyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.EmptyItem) {
        binding.title.text = item.title
    }
}