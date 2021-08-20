package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopCurrentBinding
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class TopCurrentViewHolder(
    private val binding: ItemTopCurrentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.CurrentPositionItem) {
        binding.item = item
    }
}