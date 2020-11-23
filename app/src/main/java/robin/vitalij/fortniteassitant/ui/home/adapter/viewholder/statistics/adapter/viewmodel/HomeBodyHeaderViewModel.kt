package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodyHeaderViewModel(
    val title: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.HEADER
}