package robin.vitalij.fortniteassitant.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeTitleBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.HomeHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.HomeTitleViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.HomeStatisticsSessionViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.HomeStatisticsViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeType

class HomeAdapter(
    private val openParameterList: () -> Unit,
    private val openDetailsStatistics: () -> Unit,
    private val openSessions: () -> Unit,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel>) -> Unit,
    private val openSeason: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<Home>>() {

    private val items = mutableListOf<Home>()

    fun setData(data: List<Home>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<Home>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Home> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HomeType.HEADER.id -> {
                return HomeHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_header,
                        parent,
                        false
                    ), openDetailsStatistics, openSeason
                )
            }
            HomeType.STATISTICS.id -> {
                return HomeStatisticsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_statistics,
                        parent,
                        false
                    ),
                    openDetailsStatistics, openParameterList
                )
            }
            HomeType.SESSION.id -> {
                val binding = DataBindingUtil.inflate<ItemHomeSessionBinding>(
                    inflater,
                    R.layout.item_home_session,
                    parent,
                    false
                )
                return HomeStatisticsSessionViewHolder(binding, openSessions, openSession)
            }
            HomeType.TITLE.id -> {
                val binding = DataBindingUtil.inflate<ItemHomeTitleBinding>(
                    inflater,
                    R.layout.item_home_title,
                    parent,
                    false
                )
                return HomeTitleViewHolder(binding)
            }
            else -> {
                return HomeHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_header,
                        parent,
                        false
                    ), openDetailsStatistics, openSeason
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<Home>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
