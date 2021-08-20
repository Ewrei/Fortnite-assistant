package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import java.util.*

class PlayerUserMapper :
    Mapper<FortniteProfileResponse, PlayerModel> {

    override fun transform(obj: FortniteProfileResponse): PlayerModel {
        return PlayerModel(
            userEntity = converterUserEntity(obj.stats),
        )
    }

    private fun converterUserEntity(
        playerStatsResponse: PlayerStatsResponse
    ) = UserEntity(
        playerId = playerStatsResponse.playerStatsData.account.id,
        name = playerStatsResponse.playerStatsData.account.name,
        level = playerStatsResponse.playerStatsData.battlePass.level,
        progress = playerStatsResponse.playerStatsData.battlePass.progress,
        image = playerStatsResponse.playerStatsData.image,
        all = playerStatsResponse.playerStatsData.stats.all,
        keyboardMouse = playerStatsResponse.playerStatsData.stats.keyboardMouse,
        gamepad = playerStatsResponse.playerStatsData.stats.gamepad,
        touch = playerStatsResponse.playerStatsData.stats.touch,
        timeUpdate = Date().time
    )

}