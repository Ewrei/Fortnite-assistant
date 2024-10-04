package robin.vitalij.fortniteassitant.ui.fishstats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemFishStatsBinding
import robin.vitalij.fortniteassitant.model.network.FishStatsModel

class FishStatsAdapter(
    private val onClick: (fishStats: FishStatsModel) -> Unit
) : ListAdapter<FishStatsModel, FishStatsHolder>(FISH_STATS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FishStatsHolder(
        ItemFishStatsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FishStatsHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }

    companion object {
        private val FISH_STATS_COMPARATOR =
            object : DiffUtil.ItemCallback<FishStatsModel>() {
                override fun areItemsTheSame(
                    oldItem: FishStatsModel, newItem: FishStatsModel
                ): Boolean = oldItem.name == newItem.name

                override fun areContentsTheSame(
                    oldItem: FishStatsModel,
                    newItem: FishStatsModel
                ): Boolean = oldItem == newItem
            }
    }

}
