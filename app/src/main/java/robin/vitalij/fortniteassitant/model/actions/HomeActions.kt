package robin.vitalij.fortniteassitant.model.actions

import robin.vitalij.fortniteassitant.model.DetailStatisticsModel

sealed class HomeActions {

    object OpenParameterList : HomeActions()

    object OpenDetailsStatistics : HomeActions()

    object OpenSessions : HomeActions()

    data class OpenSession(
        val sessionId: Long,
        val sessionLast: Long,
        val sessionDate: String,
        val detailsStats: List<DetailStatisticsModel>
    ) : HomeActions()

    object OpenSeason : HomeActions()
}