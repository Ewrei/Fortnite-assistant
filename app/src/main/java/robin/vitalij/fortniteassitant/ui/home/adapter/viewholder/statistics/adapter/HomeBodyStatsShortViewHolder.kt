package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatisticsShortBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyStatsShortViewHolder(private val binding: ItemHomeBodyStatisticsShortBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.StatsShortItem) {
        binding.kills.text = item.leftTop
        binding.killsTitle.text = item.leftTopTitle
        binding.shotsFired.text = item.rightTop
        binding.shotsFiredTitle.text = item.rightTopTitle
        binding.shotsHit.text = item.leftBottom
        binding.shotsHitTitle.text = item.leftBottomTitle
        binding.shotsAccuracy.text = item.rightBottom
        binding.shotsAccuracyTitle.text = item.rightBottomTitle
    }
}