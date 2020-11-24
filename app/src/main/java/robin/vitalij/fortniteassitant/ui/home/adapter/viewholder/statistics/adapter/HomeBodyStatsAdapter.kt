package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewholder.*
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsType

class HomeBodyStatsAdapter(
    private val openDetailsStatistics: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<HomeBodyStats>>() {

    private val items = arrayListOf<HomeBodyStats>()

    fun setData(data: List<HomeBodyStats>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<HomeBodyStats>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HomeBodyStats> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HomeBodyStatsType.STATISTICS.id -> {
                return HomeBodyStatsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_body_statistics,
                        parent,
                        false
                    )
                )
            }
            HomeBodyStatsType.STATISTICS_SHORT.id -> {
                return HomeBodyStatsShortViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_body_statistics_short,
                        parent,
                        false
                    )
                )
            }
            HomeBodyStatsType.DETAIL_STATISTICS.id -> {
                return HomeBodyDetailStatisticsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_body_detail_statistics,
                        parent,
                        false
                    ),
                    openDetailsStatistics
                )
            }
            HomeBodyStatsType.EMPTY.id -> {
                return HomeBodyEmptyViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_empty,
                        parent,
                        false
                    )
                )
            }
            HomeBodyStatsType.HEADER.id -> {
                return HomeBodyHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_body_header,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return HomeBodyHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_body_header,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<HomeBodyStats>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
