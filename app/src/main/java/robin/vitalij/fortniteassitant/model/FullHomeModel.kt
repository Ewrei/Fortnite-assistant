package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home

class FullHomeModel(
    val homes: List<Home>,
    val details: MutableList<DetailStatisticsModel>
)