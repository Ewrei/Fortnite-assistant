package robin.vitalij.fortniteassitant.ui.fishstats.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishStatsBinding
import robin.vitalij.fortniteassitant.model.network.FishStatsModel

class FishStatsHolder(
    private val binding: ItemFishStatsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishStatsModel) {
        binding.item = item
    }
}