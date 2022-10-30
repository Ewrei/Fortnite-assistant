package robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemBannerResultBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannerResultHolder(
    private val binding: ItemBannerResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerEntity) {
        binding.imageView.loadImage(item.bannerImage.icon)

        binding.name.text = item.name
        binding.description.text = item.description
        binding.category.text = item.category
    }
}