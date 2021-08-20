package robin.vitalij.fortniteassitant.ui.achiviements.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemAchievementBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class AchievementsHolder(
    var binding: ItemAchievementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AchievementEntity) {
        binding.item = item
    }
}