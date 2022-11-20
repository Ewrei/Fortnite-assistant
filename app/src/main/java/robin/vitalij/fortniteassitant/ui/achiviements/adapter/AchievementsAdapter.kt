package robin.vitalij.fortniteassitant.ui.achiviements.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemAchievementBinding
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementsAdapter : RecyclerView.Adapter<AchievementsHolder>() {

    private val items = mutableListOf<AchievementEntity>()

    fun updateData(data: List<AchievementEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AchievementsHolder(
        ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AchievementsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}