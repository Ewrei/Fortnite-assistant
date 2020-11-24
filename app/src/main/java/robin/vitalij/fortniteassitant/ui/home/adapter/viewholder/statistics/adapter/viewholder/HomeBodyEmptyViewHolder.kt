package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsEmptyBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyEmptyViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyHeaderViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats

class HomeBodyEmptyViewHolder(override val binding: ItemBodyStatsEmptyBinding) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodyEmptyViewModel) {
            binding.item = item.title
        }
    }
}