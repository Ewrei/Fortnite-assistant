package robin.vitalij.fortniteassitant.model.network.stats

class FortniteProfileResponse(
    val stats: PlayerStatsResponse,
    var sessionStats: PlayerStatsResponse,
    val matches: PlayerMatchesResponse
)