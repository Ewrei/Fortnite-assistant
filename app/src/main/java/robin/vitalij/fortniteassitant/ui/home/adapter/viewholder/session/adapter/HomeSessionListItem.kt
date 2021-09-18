package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import robin.vitalij.fortniteassitant.model.HistoryUserModel

sealed class HomeSessionListItem {

    class OtherItem(
        val matches: Int,
        val winRate: Double,
        val kd: Double
    ) : HomeSessionListItem()

    class SessionItem(
        val historyUserModel: HistoryUserModel
    ) : HomeSessionListItem()

}