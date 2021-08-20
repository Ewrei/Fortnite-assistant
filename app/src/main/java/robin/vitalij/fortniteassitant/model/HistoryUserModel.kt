package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.db.entity.UserEntity

data class HistoryUserModel(
    val userEntity: UserEntity,
    val averageWins: Double,
    val sessionId: Long,
    val lastSessionId: Long,
    val startTimeUpdate: Long,
    val endTimeUpdate: Long
)