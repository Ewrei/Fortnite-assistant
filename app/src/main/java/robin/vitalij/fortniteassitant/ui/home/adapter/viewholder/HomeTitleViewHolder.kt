package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeTitleBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeListItem

class HomeTitleViewHolder(private val binding: ItemHomeTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeListItem.TitleItem) {
        binding.title.text = item.title
    }
}