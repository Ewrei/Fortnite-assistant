package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder

import kotlinx.android.synthetic.main.item_home_body_detail_statistics.view.*
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyDetailStatisticsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodeDetailsStatisticsViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats

class HomeBodyDetailStatisticsViewHolder(
    override val binding: ItemHomeBodyDetailStatisticsBinding,
    private val openDetailsStatistics: () -> Unit
) :
    BaseViewHolder<HomeBodyStats>(binding) {

    override fun bind(item: HomeBodyStats) {
        if (item is HomeBodeDetailsStatisticsViewModel) {
            binding.item = item
            itemView.detailStatistics.setSafeOnClickListener { openDetailsStatistics() }
        }
    }
}