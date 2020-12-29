package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.model.network.TopUser
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.Top
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.TopHeaderViewModel
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.TopViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class TopUserMapper(private val topType: TopFullModel) :
    Mapper<List<TopUser>, List<Top>> {

    override fun transform(obj: List<TopUser>): List<Top> {
        val list = arrayListOf<Top>()
        var position = 1

        list.add(TopHeaderViewModel(topType))

        obj.forEach {
            list.add(
                TopViewModel(
                    position = position,
                    userName = it.userName ?: "Скоро добавлю ник",
                    playerId = it.accountId,
                    avatar = "",
                    value = getValue(it)
                )
            )
            position++
        }
        return list
    }

    private fun getValue(topUser: TopUser): String {
        return when (topType.topType) {
            TopType.KD -> topUser.kd.getStringFormat()
            TopType.MATCHES -> topUser.matches.getStringFormat()
            TopType.TIME_PLAYED -> topUser.minutesPlayed.getStringFormat()
            TopType.PLAYERS_OUTLIVED -> topUser.playersOutlived.getStringFormat()
            TopType.SCORE -> topUser.score.getStringFormat()
            TopType.KILLS -> topUser.kills.getStringFormat()
            TopType.DEATHS -> topUser.deaths.getStringFormat()
            TopType.WINS -> topUser.wins.getStringFormat()
            TopType.WINS_PERCENT -> topUser.winRate.getStringFormat()
            TopType.SCORE_PER_MATCH -> topUser.scorePerMatch.getStringFormat()
            TopType.SCORE_PER_MIN -> topUser.scorePerMin.getStringFormat()
            TopType.KILLS_PER_MIN -> topUser.killsPerMin.getStringFormat()
            TopType.KILLS_PER_MATCH -> topUser.killsPerMatch.getStringFormat()
        }
    }
}