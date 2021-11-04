package robin.vitalij.fortniteassitant.ui.shop.current.adapter

import android.graphics.Paint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem

class CurrentShopHolder(
    private val binding: ItemCurrentShopBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShopNewItem) {
        binding.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.image.loadBackgroundRarity(item.rarity.id)
        binding.image.loadImage(item.displayAssets.first().fullBackground)
        binding.type.text = item.displayType
        binding.price.setValueText(item.price.finalPrice)
        binding.oldPrice.setValueText(item.price.regularPrice)
        binding.oldPrice.isVisible = item.price.finalPrice != item.price.regularPrice
    }
}