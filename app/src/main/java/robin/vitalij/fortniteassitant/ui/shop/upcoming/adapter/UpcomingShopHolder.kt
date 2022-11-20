package robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemUpcomingShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming

class UpcomingShopHolder(private val binding: ItemUpcomingShopBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemShopUpcoming) {
        binding.imageView.loadBackgroundRarity(item.rarity.id)
        binding.imageView.loadImage(item.images.background)

        binding.name.text = item.name
        binding.description.text = item.description
    }
}