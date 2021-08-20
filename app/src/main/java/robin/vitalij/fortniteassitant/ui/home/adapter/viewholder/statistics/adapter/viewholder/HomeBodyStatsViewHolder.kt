package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import kotlinx.android.synthetic.main.item_home_body_statistics.view.*
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatisticsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsViewModel

class HomeBodyStatsViewHolder(override val binding: ItemHomeBodyStatisticsBinding) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodyStatsViewModel) {
            binding.item = item
            itemView.arcProgress.setAnimatedProgress(item.center)
            itemView.arcProgress.setBottomText(item.centerTitle)
        }
    }
}