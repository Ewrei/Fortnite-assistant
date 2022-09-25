package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeBodyStatisticsBinding
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeBodyStatsViewHolder(private val binding: ItemHomeBodyStatisticsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBodyStatsListItem.StatsItem) {
        binding.leftTop.text = item.leftTop
        binding.leftTopTitle.text = item.leftTopTitle

        binding.rightTop.text = item.rightTop
        binding.rightTopTitle.text = item.rightTopTitle

        binding.leftBottom.text = item.leftBottom
        binding.leftBottomTitle.text = item.leftBottomTitle

        binding.rightBottom.text = item.rightBottom
        binding.rightBottomTitle.text = item.rightBottomTitle

        binding.arcProgress.setAnimatedProgress(item.center)
        binding.arcProgress.setBottomText(item.centerTitle)
    }
}