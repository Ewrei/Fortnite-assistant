package robin.vitalij.fortniteassitant.ui.fishstats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.FishStatsModel

class FishStatsAdapter(
    private val onClick: (fishStats: FishStatsModel) -> Unit
) : RecyclerView.Adapter<FishStatsHolder>() {

    private val items = mutableListOf<FishStatsModel>()

    fun setData(data: List<FishStatsModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FishStatsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fish_stats,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FishStatsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
