package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_profile_body.view.*
import kotlinx.android.synthetic.main.recycler_view.view.*
import robin.vitalij.fortniteassitant.databinding.ItemHomeStatisticsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeStatisticsViewModel

class HomeStatisticsViewHolder(
    override val binding: ItemHomeStatisticsBinding,
    private val openDetailsStatistics:() -> Unit
) : BaseViewHolder<Home>(binding) {

    override fun bind(item: Home) {
        if (item is HomeStatisticsViewModel) {
            binding.item = item

            item.all.let(::initAdapter)

            itemView.allStats.setOnClickListener {
                item.all.let(::initAdapter)
            }

            itemView.keyboardMouse.setOnClickListener {
                item.keyboardMouse.let(::initAdapter)
            }

            itemView.gamepad.setOnClickListener {
                item.gamepad.let(::initAdapter)
            }

            itemView.touch.setOnClickListener {
                item.touch.let(::initAdapter)
            }
        }
    }

    private fun initAdapter(list: List<HomeBodyStats>) {
        itemView.recyclerView.run {
            adapter = HomeBodyStatsAdapter(openDetailsStatistics)
            (adapter as HomeBodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}