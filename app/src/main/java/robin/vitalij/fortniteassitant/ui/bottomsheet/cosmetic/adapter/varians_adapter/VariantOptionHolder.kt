package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.varians_adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemOptionBinding
import robin.vitalij.fortniteassitant.db.entity.Option

class VariantOptionHolder(
    private val binding: ItemOptionBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Option) {
        binding.cardView.layoutParams.width = widthPixels
        binding.cardView.requestLayout()

        binding.image.loadImage(item.image)
        binding.inventoryName.text = item.name
    }
}