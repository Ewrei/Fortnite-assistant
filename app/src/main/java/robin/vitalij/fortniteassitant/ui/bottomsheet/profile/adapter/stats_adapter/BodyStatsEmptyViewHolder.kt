package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsEmptyBinding

class BodyStatsEmptyViewHolder(
    private val binding: ItemBodyStatsEmptyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BodyStatsListItem.EmptyItem) {
        binding.title.text = item.emptyTitle
    }
}