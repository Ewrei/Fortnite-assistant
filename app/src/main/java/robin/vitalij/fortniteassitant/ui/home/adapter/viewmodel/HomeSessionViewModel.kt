package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession

data class HomeSessionViewModel(
    val sessions: List<HomeSession>
) : Home {
    override fun getType() = HomeType.SESSION
}