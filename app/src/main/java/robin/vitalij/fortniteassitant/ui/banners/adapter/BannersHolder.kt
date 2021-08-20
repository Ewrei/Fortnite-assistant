package robin.vitalij.fortniteassitant.ui.banners.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBannerBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsNewBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannersHolder(
    var binding: ItemBannerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerEntity) {
        binding.item = item
    }
}