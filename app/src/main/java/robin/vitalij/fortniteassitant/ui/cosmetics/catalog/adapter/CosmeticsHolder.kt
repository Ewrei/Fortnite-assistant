package robin.vitalij.fortniteassitant.ui.cosmetics.catalog.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImages
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity

class CosmeticsHolder(
    private val binding: ItemCosmeticsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsEntity) {
        binding.name.text = item.name
        binding.type.text = item.type.displayValue

        binding.image.loadImages(item.images)
        binding.image.loadBackgroundRarity(item.rarity?.value)
    }
}