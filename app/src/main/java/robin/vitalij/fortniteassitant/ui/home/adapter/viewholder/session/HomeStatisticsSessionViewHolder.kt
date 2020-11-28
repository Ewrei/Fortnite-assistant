package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session

import kotlinx.android.synthetic.main.item_home_session.view.*
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.HomeSessionAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeSessionViewModel

class HomeStatisticsSessionViewHolder(
    override val binding: ItemHomeSessionBinding,
    private val openSessions: () -> Unit,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String) -> Unit
) : BaseViewHolder<Home>(binding) {

    override fun bind(item: Home) {
        if (item is HomeSessionViewModel) {
            binding.item = item
            itemView.recyclerView.run {
                adapter = createSessionAdapter(item.sessions)
            }
        }
    }

    private fun createSessionAdapter(list: List<HomeSession>): HomeSessionAdapter {
        val adapter = HomeSessionAdapter(
            openSessions = openSessions,
            openSession = openSession
        )
        if (list.isNotEmpty()) {
            adapter.setData(list)
        }
        return adapter
    }
}