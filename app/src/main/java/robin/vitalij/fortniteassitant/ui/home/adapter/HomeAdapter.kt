package robin.vitalij.fortniteassitant.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemHomeHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeStatisticsBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeTitleBinding
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.HomeHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.HomeTitleViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.HomeStatisticsSessionViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.HomeStatisticsViewHolder

class HomeAdapter(
    private val onHomeAction: (homeActions: HomeActions) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<HomeListItem>()

    fun setData(data: List<HomeListItem>) {
        items.clear()
        items.addAll(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<HomeListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_home_header -> {
                HomeHeaderViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    onHomeAction
                )
            }
            R.layout.item_home_session -> {
                HomeStatisticsSessionViewHolder(
                    ItemHomeSessionBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    onHomeAction
                )
            }
            R.layout.item_home_statistics -> {
                HomeStatisticsViewHolder(
                    ItemHomeStatisticsBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    onHomeAction
                )
            }
            R.layout.item_home_title -> {
                HomeTitleViewHolder(
                    ItemHomeTitleBinding.inflate(
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
            is HomeListItem.HeaderItem -> (holder as HomeHeaderViewHolder).bind(item)
            is HomeListItem.SessionItem -> (holder as HomeStatisticsSessionViewHolder).bind(item)
            is HomeListItem.StatisticsItem -> (holder as HomeStatisticsViewHolder).bind(item)
            is HomeListItem.TitleItem -> (holder as HomeTitleViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is HomeListItem.HeaderItem -> R.layout.item_home_header
        is HomeListItem.SessionItem -> R.layout.item_home_session
        is HomeListItem.StatisticsItem -> R.layout.item_home_statistics
        is HomeListItem.TitleItem -> R.layout.item_home_title
    }
}
