package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopHeaderBinding
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class TopHeaderViewHolder(
    private val binding: ItemTopHeaderBinding,
    private val onTopClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.HeaderItem) {
        binding.item = item.topFullModel
        itemView.setOnClickListener {
            onTopClick()
        }
    }
}