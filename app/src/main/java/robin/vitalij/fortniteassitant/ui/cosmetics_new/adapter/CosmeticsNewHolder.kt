package robin.vitalij.fortniteassitant.ui.cosmetics_new.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImages
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsNewBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

class CosmeticsNewHolder(
    private val binding: ItemCosmeticsNewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsNewEntity) {
        binding.name.text = item.name
        binding.type.text = item.type.displayValue

        binding.image.loadImages(item.images)
        binding.image.loadBackgroundRarity(item.rarity?.value)
    }
}