package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.utils.TextUtils

fun UserEntity.differenceUser(userEntityLast: UserEntity): UserEntity {
     return UserEntity(
        playerId = userEntityLast.playerId,
        name = userEntityLast.name,
        progress = userEntityLast.progress,
        timeUpdate = userEntityLast.timeUpdate,
        level = userEntityLast.level,
        image = userEntityLast.image,
        all = getStatsTypeDevice(this.all, userEntityLast.all),
        keyboardMouse = getStatsTypeDevice(
            this.keyboardMouse,
            userEntityLast.keyboardMouse
        ),
        gamepad = getStatsTypeDevice(this.gamepad, userEntityLast.gamepad),
        touch = getStatsTypeDevice(this.touch, userEntityLast.touch)
    )
}

private fun getStatsTypeDevice(
    oneStatsTypeDevice: StatsTypeDevice?,
    lastStatsTypeDevice: StatsTypeDevice?
): StatsTypeDevice? {
    return if (oneStatsTypeDevice == null && lastStatsTypeDevice == null) {
        null
    } else if (oneStatsTypeDevice == null && lastStatsTypeDevice != null) {
        lastStatsTypeDevice
    } else {
        StatsTypeDevice(
            overall = getOverall(oneStatsTypeDevice?.overall, lastStatsTypeDevice?.overall),
            solo = getSolo(oneStatsTypeDevice?.solo, lastStatsTypeDevice?.solo),
            duo = getDuo(oneStatsTypeDevice?.duo, lastStatsTypeDevice?.duo),
            trio = getTrio(oneStatsTypeDevice?.trio, lastStatsTypeDevice?.trio),
            squad = getTrio(oneStatsTypeDevice?.squad, lastStatsTypeDevice?.squad),
            ltm = getLtm(oneStatsTypeDevice?.ltm, lastStatsTypeDevice?.ltm)
        )
    }
}

private fun getOverall(overall: Overall?, overallLast: Overall?): Overall? {
    return if (overall == null && overallLast == null) {
        null
    } else if (overall == null && overallLast != null) {
        overallLast
    } else if (overall != null && overallLast != null) {
        Overall(
            score = overallLast.score - overall.score,
            scorePerMin = overallLast.scorePerMin,
            scorePerMatch = overallLast.scorePerMatch,
            wins = overallLast.wins - overall.wins,
            top3 = overallLast.top3 - overall.top3,
            top5 = overallLast.top5 - overall.top5,
            top6 = overallLast.top6 - overall.top6,
            top10 = overallLast.top10 - overall.top10,
            top12 = overallLast.top12 - overall.top12,
            top25 = overallLast.top25 - overall.top25,
            kills = overallLast.kills - overall.kills,
            killsPerMin = overallLast.killsPerMin,
            killsPerMatch = overallLast.killsPerMatch,
            deaths = overallLast.deaths - overall.deaths,
            kd = 0.0,
            matches = overallLast.matches - overall.matches,
            winRate = 0.0,
            minutesPlayed = overallLast.minutesPlayed - overall.minutesPlayed,
            playersOutlived = overallLast.playersOutlived - overall.playersOutlived,
            lastModified = overallLast.lastModified
        ).apply {
            kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
            winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
        }
    } else {
        return null
    }
}

private fun getSolo(overall: SoloMatches?, overallLast: SoloMatches?): SoloMatches? {
    return if (overall == null && overallLast == null) {
        null
    } else if (overall == null && overallLast != null) {
        overallLast
    } else if (overall != null && overallLast != null) {
        SoloMatches(
            score = overallLast.score - overall.score,
            scorePerMin = overallLast.scorePerMin,
            scorePerMatch = overallLast.scorePerMatch,
            wins = overallLast.wins - overall.wins,
            top10 = overallLast.top10 - overall.top10,
            top25 = overallLast.top25 - overall.top25,
            kills = overallLast.kills - overall.kills,
            killsPerMin = overallLast.killsPerMin,
            killsPerMatch = overallLast.killsPerMatch,
            deaths = overallLast.deaths - overall.deaths,
            kd = 0.0,
            matches = overallLast.matches - overall.matches,
            winRate = 0.0,
            minutesPlayed = overallLast.minutesPlayed - overall.minutesPlayed,
            playersOutlived = overallLast.playersOutlived - overall.playersOutlived,
            lastModified = overallLast.lastModified
        ).apply {
            kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
            winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
        }
    } else {
        return null
    }
}

private fun getDuo(overall: DuoMatches?, overallLast: DuoMatches?): DuoMatches? {
    return if (overall == null && overallLast == null) {
        null
    } else if (overall == null && overallLast != null) {
        overallLast
    } else if (overall != null && overallLast != null) {
        DuoMatches(
            score = overallLast.score - overall.score,
            scorePerMin = overallLast.scorePerMin,
            scorePerMatch = overallLast.scorePerMatch,
            wins = overallLast.wins - overall.wins,
            top5 = overallLast.top5 - overall.top5,
            top12 = overallLast.top12 - overall.top12,
            kills = overallLast.kills - overall.kills,
            killsPerMin = overallLast.killsPerMin,
            killsPerMatch = overallLast.killsPerMatch,
            deaths = overallLast.deaths - overall.deaths,
            kd = 0.0,
            matches = overallLast.matches - overall.matches,
            winRate = 0.0,
            minutesPlayed = overallLast.minutesPlayed - overall.minutesPlayed,
            playersOutlived = overallLast.playersOutlived - overall.playersOutlived,
            lastModified = overallLast.lastModified
        ).apply {
            kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
            winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
        }
    } else {
        return null
    }
}

private fun getTrio(overall: TrioMatches?, overallLast: TrioMatches?): TrioMatches? {
    return if (overall == null && overallLast == null) {
        null
    } else if (overall == null && overallLast != null) {
        overallLast
    } else if (overall != null && overallLast != null) {
        TrioMatches(
            score = overallLast.score - overall.score,
            scorePerMin = overallLast.scorePerMin,
            scorePerMatch = overallLast.scorePerMatch,
            wins = overallLast.wins - overall.wins,
            top3 = overallLast.top3 - overall.top3,
            top6 = overallLast.top6 - overall.top6,
            kills = overallLast.kills - overall.kills,
            killsPerMin = overallLast.killsPerMin,
            killsPerMatch = overallLast.killsPerMatch,
            deaths = overallLast.deaths - overall.deaths,
            kd = 0.0,
            matches = overallLast.matches - overall.matches,
            winRate = 0.0,
            minutesPlayed = overallLast.minutesPlayed - overall.minutesPlayed,
            playersOutlived = overallLast.playersOutlived - overall.playersOutlived,
            lastModified = overallLast.lastModified
        ).apply {
            kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
            winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
        }
    } else {
        return null
    }
}

private fun getLtm(overall: Ltm?, overallLast: Ltm?): Ltm? {
    return if (overall == null && overallLast == null) {
        null
    } else if (overall == null && overallLast != null) {
        overallLast
    } else if (overall != null && overallLast != null) {
        Ltm(
            score = overallLast.score - overall.score,
            scorePerMin = overallLast.scorePerMin,
            scorePerMatch = overallLast.scorePerMatch,
            wins = overallLast.wins - overall.wins,
            kills = overallLast.kills - overall.kills,
            killsPerMin = overallLast.killsPerMin,
            killsPerMatch = overallLast.killsPerMatch,
            deaths = overallLast.deaths - overall.deaths,
            kd = 0.0,
            matches = overallLast.matches - overall.matches,
            winRate = 0.0,
            minutesPlayed = overallLast.minutesPlayed - overall.minutesPlayed,
            playersOutlived = overallLast.playersOutlived - overall.playersOutlived,
            lastModified = overallLast.lastModified
        ).apply {
            kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
            winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
        }
    } else {
        return null
    }
}