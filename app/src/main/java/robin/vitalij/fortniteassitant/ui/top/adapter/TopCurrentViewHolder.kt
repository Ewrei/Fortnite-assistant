package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopCurrentBinding

class TopCurrentViewHolder(
    private val binding: ItemTopCurrentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.CurrentPositionItem) {
        binding.position.text = item.position
        binding.value.text = item.value
    }
}