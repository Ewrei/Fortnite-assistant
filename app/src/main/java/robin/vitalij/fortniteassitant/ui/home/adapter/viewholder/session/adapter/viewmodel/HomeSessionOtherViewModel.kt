package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel

class HomeSessionOtherViewModel(
    val matches: Int,
    val winRate: Double,
    val kd: Double
) : HomeSession {

    override fun getType() = HomeSessionType.OTHER

}