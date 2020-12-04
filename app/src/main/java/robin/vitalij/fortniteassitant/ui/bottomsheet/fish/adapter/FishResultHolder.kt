package robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.databinding.ItemFishResultBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponResultBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class FishResultHolder(
    var binding: ItemFishResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishEntity) {
        binding.item = item
    }
}