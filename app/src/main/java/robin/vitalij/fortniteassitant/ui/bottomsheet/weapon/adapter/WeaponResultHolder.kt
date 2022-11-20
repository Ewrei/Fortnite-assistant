package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponResultBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponResultHolder(
    private val binding: ItemWeaponResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeaponEntity) {
        binding.imageView.loadImage(item.background)

        binding.name.text = item.name
        binding.description.text = item.description

        binding.dmgPB.text = item.dmgPB.getStringFormat()
        binding.firingRate.text = item.firingRate.getStringFormat()
        binding.clipSize.text = item.clipSize.getStringFormat()
        binding.reloadTime.text = item.reloadTime.getStringFormat()
        binding.bulletsPerCartridge.text = item.bulletsPerCartridge.getStringFormat()
        binding.spread.text = item.spread.getStringFormat()
        binding.spreadDownsights.text = item.spreadDownsights.getStringFormat()
        binding.damageZoneCritical.text = item.damageZoneCritical.getStringFormat()
    }
}