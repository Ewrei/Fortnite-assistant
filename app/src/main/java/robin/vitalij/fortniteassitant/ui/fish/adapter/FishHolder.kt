package robin.vitalij.fortniteassitant.ui.fish.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity

class FishHolder(private val binding: ItemFishBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishEntity) {
        binding.image.loadImage(item.image)
        binding.image.loadBackgroundRarity(item.rarity)
    }
}