package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setTopType
import robin.vitalij.fortniteassitant.databinding.ItemTopResultContentBinding
import robin.vitalij.fortniteassitant.model.enums.TopType

class TopContentViewHolder(
    private val binding: ItemTopResultContentBinding,
    private val onClick: (topType: TopType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.ContentItem) {
        binding.name.setTopType(item.topType)

        itemView.setOnClickListener {
            onClick(item.topType)
        }
    }
}