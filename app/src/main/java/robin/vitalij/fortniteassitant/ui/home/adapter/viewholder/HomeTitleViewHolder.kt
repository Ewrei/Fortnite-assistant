package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeTitleBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeTitleViewModel

class HomeTitleViewHolder(override val binding: ItemHomeTitleBinding) :
    BaseViewHolder<Home>(binding) {

    override fun bind(item: Home) {
        if (item is HomeTitleViewModel) {
            binding.item = item
        }
    }
}