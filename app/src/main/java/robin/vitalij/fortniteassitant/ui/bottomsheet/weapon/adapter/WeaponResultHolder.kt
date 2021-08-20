package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponResultBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponResultHolder(
    var binding: ItemWeaponResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeaponEntity) {
        binding.item = item
    }
}