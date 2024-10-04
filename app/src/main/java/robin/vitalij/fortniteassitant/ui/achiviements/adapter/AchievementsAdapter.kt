package robin.vitalij.fortniteassitant.ui.achiviements.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemAchievementBinding
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementsAdapter :
    ListAdapter<AchievementEntity, AchievementsHolder>(ACHIEVEMENT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AchievementsHolder(
        ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AchievementsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val ACHIEVEMENT_COMPARATOR = object : DiffUtil.ItemCallback<AchievementEntity>() {
            override fun areItemsTheSame(
                oldItem: AchievementEntity, newItem: AchievementEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: AchievementEntity,
                newItem: AchievementEntity
            ): Boolean = oldItem == newItem
        }
    }

}