package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view.view.*
import robin.vitalij.fortniteassitant.databinding.ItemHomeStatisticsBinding
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeListItem
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class HomeStatisticsViewHolder(
    private val binding: ItemHomeStatisticsBinding,
    private val onHomeAction: (homeActions: HomeActions) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeListItem.StatisticsItem) {
        item.all.let(::initAdapter)

        binding.typeStatGroupInclude.allStats.setOnClickListener {
            item.all.let(::initAdapter)
        }

        binding.typeStatGroupInclude.keyboardMouse.setOnClickListener {
            item.keyboardMouse.let(::initAdapter)
        }

        binding.typeStatGroupInclude.gamepad.setOnClickListener {
            item.gamepad.let(::initAdapter)
        }

        binding.typeStatGroupInclude.touch.setOnClickListener {
            item.touch.let(::initAdapter)
        }
    }

    private fun initAdapter(list: List<HomeBodyStatsListItem>) {
        itemView.recyclerView.run {
            adapter = HomeBodyStatsAdapter(openDetailsStatistics = {
                onHomeAction(HomeActions.OpenDetailsStatistics)
            }, openParameterList = {
                onHomeAction(HomeActions.OpenParameterList)
            })
            (adapter as HomeBodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}