package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.databinding.ItemOtherItemsDetailsBinding
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel

class OtherItemsDetailsHolder(
    private val binding: ItemOtherItemsDetailsBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GrantedModel) {
        binding.item = item
        binding.cardView.layoutParams.width = widthPixels
        binding.cardView.requestLayout()

        binding.image.loadBackgroundRarity(item.rarity.id)
    }
}