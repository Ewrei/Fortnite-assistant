package robin.vitalij.fortniteassitant.model.actions

import robin.vitalij.fortniteassitant.model.DetailStatisticsModel

sealed class HomeActions {

    data object OpenParameterList : HomeActions()

    data object OpenDetailsStatistics : HomeActions()

    data object OpenSessions : HomeActions()

    data class OpenSession(
        val sessionId: Long,
        val sessionLast: Long,
        val sessionDate: String,
        val detailsStats: List<DetailStatisticsModel>
    ) : HomeActions()

    data object OpenSeason : HomeActions()
}