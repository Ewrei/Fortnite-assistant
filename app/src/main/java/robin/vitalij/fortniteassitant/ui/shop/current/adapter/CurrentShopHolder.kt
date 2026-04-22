package robin.vitalij.fortniteassitant.ui.shop.current.adapter

import android.graphics.Paint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.loadImage
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopEntry

class CurrentShopHolder(
    private val binding: ItemCurrentShopBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShopEntry) {
        binding.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

//        binding.image.loadBackgroundRarity(item.rarity?.id)
        binding.image.loadImage(item.bundle?.image ?: item.brItems?.firstOrNull()?.images?.featured ?: item.brItems?.firstOrNull()?.images?.icon ?: item.tracks?.firstOrNull()?.albumArt)
        binding.type.text = item.bundle?.name ?: item.brItems?.firstOrNull()?.name ?: item.tracks?.firstOrNull()?.title
        binding.price.setValueText(item.finalPrice)
        binding.oldPrice.setValueText(item.regularPrice)
        binding.oldPrice.isVisible = item.finalPrice != item.regularPrice
    }
}