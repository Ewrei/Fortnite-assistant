package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerScheduleBinding
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerStatisticsBinding

class ComparisonStatisticsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ComparisonStatisticsListItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ComparisonStatisticsListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_comparison_player_schedule -> {
                ComparisonScheduleViewHolder(
                    ItemComparisonPlayerScheduleBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                )
            }

            R.layout.item_comparison_player_header -> {
                ComparisonStatisticsHeaderViewHolder(
                    ItemComparisonPlayerHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                )
            }

            R.layout.item_comparison_player_statistics -> {
                ComparisonStatisticsViewHolder(
                    ItemComparisonPlayerStatisticsBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                )
            }

            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ComparisonStatisticsListItem.ScheduleItem -> (holder as ComparisonScheduleViewHolder).bind(
                item
            )

            is ComparisonStatisticsListItem.HeaderItem -> (holder as ComparisonStatisticsHeaderViewHolder).bind(
                item
            )

            is ComparisonStatisticsListItem.StatisticsItem -> (holder as ComparisonStatisticsViewHolder).bind(
                item
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ComparisonStatisticsListItem.ScheduleItem -> R.layout.item_comparison_player_schedule
            is ComparisonStatisticsListItem.HeaderItem -> R.layout.item_comparison_player_header
            is ComparisonStatisticsListItem.StatisticsItem -> R.layout.item_comparison_player_statistics
        }
    }

    override fun getItemCount(): Int = items.size

}
