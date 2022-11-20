package robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.databinding.ItemFishResultBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponResultBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class FishResultHolder(
    private val binding: ItemFishResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishEntity) {
        binding.imageView.loadImage(item.image)
        binding.imageView.loadBackgroundRarity(item.rarity)

        binding.name.text = item.name
        binding.description.text = item.description
        binding.details.text = item.details
        binding.required.isVisible = item.needsProFishingRod
        binding.professionalFishingRod.isVisible = item.needsProFishingRod

        binding.maxStackSize.text = item.maxStackSize.getStringFormat()
        binding.fullSize.text = item.getFullSize()
    }
}