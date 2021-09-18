package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.HomeSessionListItem

data class HomeSessionViewModel(
    val sessions: List<HomeSessionListItem>
) : Home {
    override fun getType() = HomeType.SESSION
}