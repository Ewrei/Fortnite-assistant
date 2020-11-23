package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

data class HomeHeaderViewModel(
    val avatarUrl: String,
    val userName: String,
    val playerId: String,
    val matchesPlayed: Int,
    val timeHours: Double
) : Home {
    override fun getType() = HomeType.HEADER
}