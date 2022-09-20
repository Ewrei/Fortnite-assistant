package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.model.network.TopUser
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class TopUserMapper(
    private val topType: TopFullModel,
    private val playerId: String,
    private val resourceProvider: ResourceProvider
) : Mapper<List<TopUser>, List<TopListItem>> {

    override fun transform(obj: List<TopUser>): List<TopListItem> {
        val list = mutableListOf<TopListItem>()
        val tops = mutableListOf<TopListItem.PlayerItem>()

        var position = 1

        list.add(TopListItem.HeaderItem(topType))

        obj.forEach {
            tops.add(
                TopListItem.PlayerItem(
                    position = position,
                    userName = it.userName ?: "Скоро добавлю ник",
                    playerId = it.accountId,
                    avatar = "",
                    value = getValue(it)
                )
            )
            position++
        }

        tops.find { it.playerId == playerId }?.let {
            list.add(
                TopListItem.CurrentPositionItem(
                    position = resourceProvider.getString(
                        R.string.your_current_position_format,
                        it.position.getStringFormat()
                    ),
                    value = resourceProvider.getString(
                        R.string.your_value_format,
                        it.value
                    )
                )
            )
        }

        list.addAll(tops)

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