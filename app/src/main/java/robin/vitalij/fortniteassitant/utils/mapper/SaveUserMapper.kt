package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.common.extensions.converterUserEntity
import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.model.SaveUserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerMatchesResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import java.util.*

class SaveUserMapper : Mapper<FortniteProfileResponse, SaveUserModel> {

    override fun transform(obj: FortniteProfileResponse): SaveUserModel {

        return SaveUserModel(
            userEntity = obj.stats.converterUserEntity(),
            session = PlayerSession(
                obj.stats.playerStatsData.account.id,
                Date().time
            ),
            matches = getMatches(obj.matches, obj.stats.playerStatsData.account.id)
        )
    }

    private fun getMatches(
        playerMatchesResponse: PlayerMatchesResponse,
        playerId: String
    ): List<MatchEntity> {
        val list = arrayListOf<MatchEntity>()
        playerMatchesResponse.matches.filter { !it.mode.isBlank() }.forEach {
            list.add(
                MatchEntity(
                    platform = it.platform,
                    date = it.date,
                    mode = it.mode,
                    readableName = it.readableName,
                    kills = it.kills,
                    matchesPlayed = it.matchesplayed,
                    minutesPlayed = it.minutesplayed,
                    playersoutLived = it.playersoutlived,
                    score = it.score,
                    placetop1 = it.placetop1,
                    placetop3 = it.placetop3,
                    placetop5 = it.placetop5,
                    placetop6 = it.placetop6,
                    placetop10 = it.placetop10,
                    placetop12 = it.placetop12,
                    placetop25 = it.placetop25
                ).apply {
                    this.playerId = playerId
                    this.id = "${date}_${playerId}"
                }
            )
        }
        return list
    }
}