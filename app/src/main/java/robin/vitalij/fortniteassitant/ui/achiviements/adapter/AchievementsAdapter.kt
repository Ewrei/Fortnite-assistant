package robin.vitalij.fortniteassitant.ui.achiviements.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementsAdapter(
) : RecyclerView.Adapter<AchievementsHolder>() {

    private val items = arrayListOf<AchievementEntity>()

    fun setData(data: List<AchievementEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AchievementsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_achievement,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AchievementsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
