package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.*

class HomeBodyStatsAdapter(
    private val openDetailsStatistics: () -> Unit,
    private val openParameterList: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<HomeBodyStatsListItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<HomeBodyStatsListItem>) {
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
                HomeBodyEmptyViewHolder(
                    ItemBodyStatsEmptyBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_home_body_header -> {
                HomeBodyHeaderViewHolder(
                    ItemHomeBodyHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_home_body_statistics_short -> {
                HomeBodyStatsShortViewHolder(
                    ItemHomeBodyStatisticsShortBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_home_body_stats_small -> {
                HomeBodyStatsSmallViewHolder(
                    ItemHomeBodyStatsSmallBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_home_body_statistics -> {
                HomeBodyStatsViewHolder(
                    ItemHomeBodyStatisticsBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_home_body_detail_statistics -> {
                HomeBodyDetailStatisticsViewHolder(
                    ItemHomeBodyDetailStatisticsBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    openDetailsStatistics,
                    openParameterList
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeBodyStatsListItem.EmptyItem -> (holder as HomeBodyEmptyViewHolder).bind(item)
            is HomeBodyStatsListItem.HeaderItem -> (holder as HomeBodyHeaderViewHolder).bind(item)
            is HomeBodyStatsListItem.StatsShortItem -> (holder as HomeBodyStatsShortViewHolder).bind(
                item
            )
            is HomeBodyStatsListItem.StatsSmallItem -> (holder as HomeBodyStatsSmallViewHolder).bind(
                item
            )
            is HomeBodyStatsListItem.StatsItem -> (holder as HomeBodyStatsViewHolder).bind(item)
            is HomeBodyStatsListItem.DetailStatisticsItem -> (holder as HomeBodyDetailStatisticsViewHolder).bind(
                item
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is HomeBodyStatsListItem.EmptyItem -> R.layout.item_body_stats_empty
        is HomeBodyStatsListItem.HeaderItem -> R.layout.item_home_body_header
        is HomeBodyStatsListItem.StatsShortItem -> R.layout.item_home_body_statistics_short
        is HomeBodyStatsListItem.StatsSmallItem -> R.layout.item_home_body_stats_small
        is HomeBodyStatsListItem.StatsItem -> R.layout.item_home_body_statistics
        is HomeBodyStatsListItem.DetailStatisticsItem -> R.layout.item_home_body_detail_statistics
    }

}