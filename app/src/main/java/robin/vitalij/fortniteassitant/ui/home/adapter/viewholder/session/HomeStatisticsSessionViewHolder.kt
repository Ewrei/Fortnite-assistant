package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeListItem
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.HomeSessionAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.HomeSessionListItem

class HomeStatisticsSessionViewHolder(
    private val binding: ItemHomeSessionBinding,
    private val onHomeAction: (homeActions: HomeActions) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeListItem.SessionItem) {
        binding.recyclerView.run {
            adapter = createSessionAdapter(item.sessions)
        }
    }

    private fun createSessionAdapter(list: List<HomeSessionListItem>): HomeSessionAdapter {
        val adapter = HomeSessionAdapter(
            openSessions = {
                onHomeAction(HomeActions.OpenSessions)
            },
            openSession = { sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel> ->
                onHomeAction(
                    HomeActions.OpenSession(
                        sessionId,
                        sessionLast,
                        sessionDate,
                        detailsStats
                    )
                )
            }
        )
        if (list.isNotEmpty()) {
            adapter.setData(list)
        }
        return adapter
    }
}