package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import java.util.*

fun PlayerStatsResponse.converterUserEntity() = UserEntity(
    playerId = playerStatsData.account.id,
    name = playerStatsData.account.name,
    level = playerStatsData.battlePass.level,
    progress = playerStatsData.battlePass.progress,
    image = playerStatsData.image,
    all = playerStatsData.stats.all,
    keyboardMouse = playerStatsData.stats.keyboardMouse,
    gamepad = playerStatsData.stats.gamepad,
    touch = playerStatsData.stats.touch,
    timeUpdate = Date().time
)