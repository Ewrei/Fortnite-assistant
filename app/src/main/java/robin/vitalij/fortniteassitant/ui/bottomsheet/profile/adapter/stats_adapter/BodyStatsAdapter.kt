package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsEmptyBinding
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsLtmBinding
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsOverallBinding
import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsStatsBinding

class BodyStatsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<BodyStatsListItem>()

    fun setData(data: List<BodyStatsListItem>) {
        items.clear()
        items.addAll(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<BodyStatsListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_body_stats_empty -> {
                BodyStatsEmptyViewHolder(
                    ItemBodyStatsEmptyBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_body_stats_ltm -> {
                BodyStatsLtmViewHolder(
                    ItemBodyStatsLtmBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_body_stats_overall -> {
                BodyStatsOverallViewHolder(
                    ItemBodyStatsOverallBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_body_stats_stats -> {
                BodyStatsStatsViewHolder(
                    ItemBodyStatsStatsBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is BodyStatsListItem.EmptyItem -> (holder as BodyStatsEmptyViewHolder).bind(item)
            is BodyStatsListItem.LtmItem -> (holder as BodyStatsLtmViewHolder).bind(item)
            is BodyStatsListItem.OverallItem -> (holder as BodyStatsOverallViewHolder).bind(item)
            is BodyStatsListItem.StatsItem -> (holder as BodyStatsStatsViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is BodyStatsListItem.EmptyItem -> R.layout.item_body_stats_empty
        is BodyStatsListItem.LtmItem -> R.layout.item_body_stats_ltm
        is BodyStatsListItem.OverallItem -> R.layout.item_body_stats_overall
        is BodyStatsListItem.StatsItem -> R.layout.item_body_stats_stats
    }

}