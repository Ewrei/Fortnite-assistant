package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatsSmallBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyStatsSmallViewHolder(private val binding: ItemHomeBodyStatsSmallBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.StatsSmallItem) {
        binding.kills.text = item.leftTop
        binding.killsTitle.text = item.leftTopTitle
        binding.shotsFired.text = item.rightTop
        binding.shotsFiredTitle.text = item.rightTopTitle
    }
}