package robin.vitalij.fortniteassitant.ui.achiviements.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemAchievementBinding
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementsHolder(
    private val binding: ItemAchievementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AchievementEntity) {
        binding.item = item
    }
}