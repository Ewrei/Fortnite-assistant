package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.ui.home.adapter.HomeListItem

class FullHomeModel(
    val homes: List<HomeListItem>,
    val details: MutableList<DetailStatisticsModel>
)