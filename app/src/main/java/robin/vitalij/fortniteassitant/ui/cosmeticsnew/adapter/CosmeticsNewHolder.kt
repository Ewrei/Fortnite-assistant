package robin.vitalij.fortniteassitant.ui.cosmeticsnew.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsNewBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class CosmeticsNewHolder(
    var binding: ItemCosmeticsNewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsNewEntity) {
        binding.item = item
    }
}