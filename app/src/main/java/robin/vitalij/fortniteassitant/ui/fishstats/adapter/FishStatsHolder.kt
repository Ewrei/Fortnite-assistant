package robin.vitalij.fortniteassitant.ui.fishstats.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemFishStatsBinding
import robin.vitalij.fortniteassitant.model.network.FishStatsModel

class FishStatsHolder(
    private val binding: ItemFishStatsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishStatsModel) {
        binding.image.loadImage(item.image)
        binding.centimeters.text = binding.root.context.getString(R.string.centimeters_format, item.length)
    }

}