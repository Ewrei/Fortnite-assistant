package robin.vitalij.fortniteassitant.ui.cosmetics.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCatalogCosmeticsBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsNewBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.enums.ShopType

class CatalogCosmeticsHolder(
    var binding: ItemCatalogCosmeticsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShopType) {
        binding.item = item
    }
}