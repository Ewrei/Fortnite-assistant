package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsStatsBinding

class BodyStatsStatsViewHolder(
    private val binding: ItemBodyStatsStatsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BodyStatsListItem.StatsItem) {
        binding.title.text = item.title

        binding.matches.text = item.matches.getStringFormat()
        binding.kills.text = item.kills.getStringFormat()
        binding.deaths.text = item.deaths.getStringFormat()
        binding.killsPerMin.text = item.killsPerMin.getStringFormat()
        binding.killsPerMatch.text = item.killsPerMatch.getStringFormat()
        binding.kd.text = item.kd.getStringFormat()
        binding.topOne.text = item.wins.getStringFormat()
        binding.top10.text = item.valueOne
        binding.top10Title.text = item.titleOne
        binding.topTwentyFive.text = item.valueTwo
        binding.topTwentyFiveTitle.text = item.titleTwo

        binding.winRate.setTextPercent(item.winRate)
    }
}