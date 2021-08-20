package robin.vitalij.fortniteassitant.ui.weapons.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponHolder(
    private val binding: ItemWeaponBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeaponEntity) {
        binding.item = item
    }
}