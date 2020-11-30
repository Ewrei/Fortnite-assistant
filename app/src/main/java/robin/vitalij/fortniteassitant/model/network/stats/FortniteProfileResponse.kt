package robin.vitalij.fortniteassitant.model.network.stats

class FortniteProfileResponse(
    val stats: PlayerStatsResponse,
    val matches: PlayerMatchesResponse,
    var avatar: String = ""
)