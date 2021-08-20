package robin.vitalij.fortniteassitant.ui.fishing.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class FishHolder(
    var binding: ItemFishBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishEntity) {
        binding.item = item
    }
}