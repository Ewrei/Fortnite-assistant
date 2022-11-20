package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsLtmBinding

class BodyStatsLtmViewHolder(
    private val binding: ItemBodyStatsLtmBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BodyStatsListItem.LtmItem) {
        binding.title.text = item.title
        binding.matches.text = item.matches.matches.getStringFormat()
        binding.kills.text = item.matches.kills.getStringFormat()
        binding.deaths.text = item.matches.deaths.getStringFormat()
        binding.killsPerMin.text = item.matches.killsPerMin.getStringFormat()
        binding.killsPerMatch.text = item.matches.killsPerMatch.getStringFormat()
        binding.kd.text = item.matches.kd.getStringFormat()
        binding.minutesPlayed.text = item.matches.minutesPlayed.getStringFormat()
        binding.winRate.setTextPercent(item.matches.winRate)
    }
}