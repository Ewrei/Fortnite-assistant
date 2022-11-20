package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_home_body_detail_statistics.view.*
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyDetailStatisticsBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyDetailStatisticsViewHolder(
    private val binding: ItemHomeBodyDetailStatisticsBinding,
    private val openDetailsStatistics: () -> Unit,
    private val openParameterList: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.DetailStatisticsItem) {
        binding.detailStatistics.setSafeOnClickListener { openDetailsStatistics() }
        binding.parameterList.setSafeOnClickListener { openParameterList() }
    }
}