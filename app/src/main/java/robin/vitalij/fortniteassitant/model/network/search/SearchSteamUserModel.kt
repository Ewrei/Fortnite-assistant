package robin.vitalij.fortniteassitant.model.network.search

import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsData

data class SearchSteamUserModel(
    val accountId: String,
    val name: String,
    val avatarImage: String,
    val playerStatsData: PlayerStatsData,
)