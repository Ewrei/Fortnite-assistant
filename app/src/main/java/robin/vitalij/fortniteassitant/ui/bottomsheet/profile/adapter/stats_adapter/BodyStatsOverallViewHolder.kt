package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsOverallBinding

class BodyStatsOverallViewHolder(
    private val binding: ItemBodyStatsOverallBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BodyStatsListItem.OverallItem) {
        binding.title.text = item.title

        binding.matches.text = item.matches.matches.getStringFormat()
        binding.kills.text = item.matches.kills.getStringFormat()
        binding.deaths.text = item.matches.deaths.getStringFormat()
        binding.killsPerMin.text = item.matches.killsPerMin.getStringFormat()
        binding.killsPerMatch.text = item.matches.killsPerMatch.getStringFormat()
        binding.kd.text = item.matches.kd.getStringFormat()
        binding.top3.text = item.matches.top3.getStringFormat()
        binding.topFive.text = item.matches.top5.getStringFormat()
        binding.top6.text = item.matches.top6.getStringFormat()
        binding.topTen.text = item.matches.top10.getStringFormat()
        binding.top12.text = item.matches.top12.getStringFormat()
        binding.topTwentyFive.text = item.matches.top25.getStringFormat()

        binding.winRate.setTextPercent(item.matches.winRate)
    }
}