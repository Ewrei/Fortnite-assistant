package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.repository.network.TopUser
import robin.vitalij.fortniteassitant.ui.top.adapter.TopUserModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class TopUserMapper(private val topType: TopType) :
    Mapper<List<TopUser>, List<TopUserModel>> {

    override fun transform(obj: List<TopUser>): List<TopUserModel> {
        val list = arrayListOf<TopUserModel>()
        var position = 1
        obj.forEach {
            list.add(
                TopUserModel(
                    position = position,
                    userName = it.userName,
                    playerId = it.playerId,
                    value = getValue(it)
                )
            )
            position++
        }
        return list
    }

    private fun getValue(topUser: TopUser): String {
        return ""
//        return when (topType) {
//            return else -> ""
//            TopType.TIME_PLAYED -> (topUser.timePlayed / 60).getStringFormat()
//            TopType.SCORE -> topUser.score.getStringFormat()
//            TopType.KD -> topUser.kd.getStringFormat()
//            TopType.KR -> topUser.kr.getStringFormat()
//            TopType.KILLS -> topUser.kills.getStringFormat()
//            TopType.DEATHS -> topUser.deaths.getStringFormat()
//            TopType.HEADSHOTS -> topUser.headshots.getStringFormat()
//            TopType.HEADSHOTS_PERCENT -> topUser.headshotsPercent.getStringFormat("%")
//            TopType.BOMBS_PLANED -> topUser.bombsPlanted.getStringFormat()
//            TopType.BOMBS_DEFUSED -> topUser.bombsDefused.getStringFormat()
//            TopType.MVP -> topUser.mvp.getStringFormat()
//            TopType.WINS -> topUser.wins.getStringFormat()
//            TopType.WINS_PERCENT -> topUser.wlPercentage.getStringFormat("%")
//            TopType.MATCHES_PLAYED -> topUser.matchesPlayed.getStringFormat()
//            TopType.ROUNDS -> topUser.roundsPlayed.getStringFormat()
//            TopType.ROUNDS_WON -> topUser.roundsWon.getStringFormat()
//            TopType.ROUNDS_PERCENTAGE -> topUser.roundsPercentage.getStringFormat()
    }
    //}
}