package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyDetailStatisticsBinding

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