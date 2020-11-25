package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatsSmallBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsSmallViewModel

class HomeBodyStatsSmallViewHolder(override val binding: ItemHomeBodyStatsSmallBinding) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodyStatsSmallViewModel) {
            binding.item = item
        }
    }
}