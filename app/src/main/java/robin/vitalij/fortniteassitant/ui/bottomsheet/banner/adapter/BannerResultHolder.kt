package robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.*
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class BannerResultHolder(
    var binding: ItemBannerResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerEntity) {
        binding.item = item
    }
}