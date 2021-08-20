package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodyEmptyViewModel(
    val title: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.EMPTY
}