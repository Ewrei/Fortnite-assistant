package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.model.network.stats.StatsTypeDevice
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.Profile
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.ProfileBodyViewModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.ProfileHeaderViewModel
import robin.vitalij.fortniteassitant.utils.TextUtils
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class ProfileResultMapper(
    private val resourceProvider: ResourceProvider
) :
    Mapper<FortniteProfileResponse, List<Profile>> {

    override fun transform(obj: FortniteProfileResponse): List<Profile> {
        val list = mutableListOf<Profile>()
        list.add(
            ProfileHeaderViewModel(
                avatarUrl = obj.avatar,
                userName = obj.stats.playerStatsData.account.name,
                playerId = obj.stats.playerStatsData.account.id,
                level = obj.stats.playerStatsData.battlePass.level,
                progress = obj.stats.playerStatsData.battlePass.progress,
                matches = obj.stats.playerStatsData.stats.all?.overall?.matches ?: 0,
                kd = obj.stats.playerStatsData.stats.all?.overall?.kd ?: 0.0,
                winRate = obj.stats.playerStatsData.stats.all?.overall?.winRate ?: 0.0,
                playTime = resourceProvider.getString(
                    R.string.play_time,
                    TextUtils.getAverage(
                        obj.stats.playerStatsData.stats.all?.overall?.minutesPlayed?.toDouble(),
                        60.0
                    ).getStringFormat()
                ),
                totalMatches = resourceProvider.getString(
                    R.string.matches_format,
                    obj.stats.playerStatsData.stats.all?.overall?.matches.getStringFormat()
                )
            )
        )

        list.add(
            ProfileBodyViewModel(
                getAllBodyStats(obj.stats.playerStatsData.stats.all),
                getAllBodyStats(obj.stats.playerStatsData.stats.keyboardMouse),
                getAllBodyStats(obj.stats.playerStatsData.stats.gamepad),
                getAllBodyStats(obj.stats.playerStatsData.stats.touch)
            )
        )
        return list
    }

    private fun getAllBodyStats(statsTypeDevice: StatsTypeDevice?): List<BodyStats> {
        val list = mutableListOf<BodyStats>()

        if (statsTypeDevice == null || statsTypeDevice.overall?.matches == 0) {
            list.add(BodyStatsEmpty(resourceProvider.getString(R.string.no_results_for_this_platform)))
        } else {
            statsTypeDevice.overall?.let {
                list.add(
                    BodyStatsOverall(
                        resourceProvider.getString(R.string.overall_battles),
                        it
                    )
                )
            }

            statsTypeDevice.solo?.let {
                list.add(
                    BodyStatsStats(
                        resourceProvider.getString(R.string.solo_battles),
                        score = it.score,
                        wins = it.wins,
                        kills = it.kills,
                        killsPerMin = it.killsPerMin,
                        killsPerMatch = it.killsPerMatch,
                        deaths = it.deaths,
                        kd = it.kd,
                        matches = it.matches,
                        winRate = it.winRate,
                        minutesPlayed = it.minutesPlayed,
                        titleOne = resourceProvider.getString(R.string.top_ten),
                        valueOne = it.top10.getStringFormat(),
                        titleTwo = resourceProvider.getString(R.string.top_twenty_five),
                        valueTwo = it.top25.getStringFormat()
                    )
                )
            }

            statsTypeDevice.duo?.let {
                list.add(
                    BodyStatsStats(
                        resourceProvider.getString(R.string.duo_battles),
                        score = it.score,
                        wins = it.wins,
                        kills = it.kills,
                        killsPerMin = it.killsPerMin,
                        killsPerMatch = it.killsPerMatch,
                        deaths = it.deaths,
                        kd = it.kd,
                        matches = it.matches,
                        winRate = it.winRate,
                        minutesPlayed = it.minutesPlayed,
                        titleOne = resourceProvider.getString(R.string.top_five),
                        valueOne = it.top5.getStringFormat(),
                        titleTwo = resourceProvider.getString(R.string.top_twelve),
                        valueTwo = it.top12.getStringFormat()
                    )
                )
            }

            statsTypeDevice.trio?.let {
                list.add(
                    BodyStatsStats(
                        resourceProvider.getString(R.string.trio_battles),
                        score = it.score,
                        wins = it.wins,
                        kills = it.kills,
                        killsPerMin = it.killsPerMin,
                        killsPerMatch = it.killsPerMatch,
                        deaths = it.deaths,
                        kd = it.kd,
                        matches = it.matches,
                        winRate = it.winRate,
                        minutesPlayed = it.minutesPlayed,
                        titleOne = resourceProvider.getString(R.string.top_three),
                        valueOne = it.top3.getStringFormat(),
                        titleTwo = resourceProvider.getString(R.string.top_six),
                        valueTwo = it.top6.getStringFormat()
                    )
                )
            }

            statsTypeDevice.squad?.let {
                list.add(
                    BodyStatsStats(
                        resourceProvider.getString(R.string.squad_battles),
                        score = it.score,
                        wins = it.wins,
                        kills = it.kills,
                        killsPerMin = it.killsPerMin,
                        killsPerMatch = it.killsPerMatch,
                        deaths = it.deaths,
                        kd = it.kd,
                        matches = it.matches,
                        winRate = it.winRate,
                        minutesPlayed = it.minutesPlayed,
                        titleOne = resourceProvider.getString(R.string.top_three),
                        valueOne = it.top3.getStringFormat(),
                        titleTwo = resourceProvider.getString(R.string.top_six),
                        valueTwo = it.top6.getStringFormat()
                    )
                )
            }

            statsTypeDevice.ltm?.let {
                list.add(
                    BodyStatsLtm(
                        resourceProvider.getString(R.string.ltm_battles),
                        it
                    )
                )
            }
        }

        return list
    }
}