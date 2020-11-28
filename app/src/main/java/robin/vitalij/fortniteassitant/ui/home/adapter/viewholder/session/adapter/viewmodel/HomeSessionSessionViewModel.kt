package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.HistoryUserModel

data class HomeSessionSessionViewModel(
    val historyUserModel: HistoryUserModel
): HomeSession {
    override fun getType() =  HomeSessionType.SESSION
}