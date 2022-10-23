package robin.vitalij.fortniteassitant.ui.weapons.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponHolder(
    private val binding: ItemWeaponBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeaponEntity) {
        binding.image.loadImage(item.background)

        binding.dmgPB.text = item.dmgPB.getStringFormat()
        binding.firingRate.text = item.firingRate.getStringFormat()
        binding.clipSize.text = item.clipSize.getStringFormat()
        binding.reloadTime.text = item.reloadTime.getStringFormat()
        binding.spread.text = item.spread.getStringFormat()
        binding.spreadDownsights.text = item.spreadDownsights.getStringFormat()
    }

}