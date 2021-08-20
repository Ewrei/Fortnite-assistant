package robin.vitalij.fortniteassitant.ui.banners.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBannerBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannersHolder(
    private val binding: ItemBannerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerEntity) {
        binding.item = item
    }
}