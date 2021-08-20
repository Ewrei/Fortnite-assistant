package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyHeaderViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats

class HomeBodyHeaderViewHolder(override val binding: ItemHomeBodyHeaderBinding) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodyHeaderViewModel) {
            binding.item = item
        }
    }
}