package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopResultHeaderBinding

class TopHeaderViewHolder(private val binding: ItemTopResultHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.HeaderItem) {
        binding.title.text = item.title
    }
}