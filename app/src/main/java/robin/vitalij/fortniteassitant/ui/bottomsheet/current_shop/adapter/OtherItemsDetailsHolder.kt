package robin.vitalij.fortniteassitant.ui.bottomsheet.current_shop.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTypeShop
import robin.vitalij.fortniteassitant.databinding.ItemOtherItemsDetailsBinding
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel

class OtherItemsDetailsHolder(
    private val binding: ItemOtherItemsDetailsBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GrantedModel) {
        binding.cardView.layoutParams.width = widthPixels
        binding.cardView.requestLayout()

        binding.inventoryName.text = item.name
        binding.type.setTypeShop(item.type.id)

        binding.image.loadImage(item.images.background)
        binding.image.loadBackgroundRarity(item.rarity.id)
    }
}