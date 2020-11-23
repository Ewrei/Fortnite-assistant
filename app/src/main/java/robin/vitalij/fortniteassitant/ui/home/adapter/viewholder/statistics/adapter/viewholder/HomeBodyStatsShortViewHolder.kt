package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatisticsShortBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsShortViewModel

class HomeBodyStatsShortViewHolder(override val binding: ItemHomeBodyStatisticsShortBinding) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodyStatsShortViewModel) {
            binding.item = item
        }
    }
}