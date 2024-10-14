package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerScheduleBinding
import robin.vitalij.fortniteassitant.databinding.ItemComparisonPlayerStatisticsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder.ComparisonScheduleViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder.ComparisonStatisticsHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewholder.ComparisonStatisticsViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayerType

class ComparisonStatisticsAdapter : RecyclerView.Adapter<BaseViewHolder<ComparisonPlayer>>() {

    private val items = mutableListOf<ComparisonPlayer>()

    fun setData(data: List<ComparisonPlayer>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ComparisonPlayer> {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ComparisonPlayerType.SCHEDULE.id -> {
                return ComparisonScheduleViewHolder(
                    ItemComparisonPlayerScheduleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ComparisonPlayerType.STATISTICS.id -> {
                val binding = DataBindingUtil.inflate<ItemComparisonPlayerStatisticsBinding>(
                    inflater,
                    R.layout.item_comparison_player_statistics,
                    parent,
                    false
                )
                return ComparisonStatisticsViewHolder(binding)
            }

            ComparisonPlayerType.HEADER.id -> {
                val binding =
                    DataBindingUtil.inflate<ItemComparisonPlayerHeaderBinding>(
                        inflater,
                        R.layout.item_comparison_player_header,
                        parent,
                        false
                    )
                return ComparisonStatisticsHeaderViewHolder(binding)
            }

            else -> {
                return ComparisonScheduleViewHolder(
                    ItemComparisonPlayerScheduleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<ComparisonPlayer>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
