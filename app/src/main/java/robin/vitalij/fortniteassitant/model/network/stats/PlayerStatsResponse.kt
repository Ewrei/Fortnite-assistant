package robin.vitalij.fortniteassitant.model.network.stats

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.utils.TextUtils

class PlayerStatsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val playerStatsData: PlayerStatsData,
)

class PlayerStatsData(
    @SerializedName("image") var image: String?,
    @SerializedName("account") val account: Account,
    @SerializedName("battlePass") val battlePass: BattlePass,
    @SerializedName("stats") val stats: Stats,
)

class Account(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

class BattlePass(
    @SerializedName("level") val level: Int,
    @SerializedName("progress") val progress: Int
)

class Stats(
    @SerializedName("all") val all: StatsTypeDevice?,
    @SerializedName("keyboardMouse") val keyboardMouse: StatsTypeDevice?,
    @SerializedName("gamepad") val gamepad: StatsTypeDevice?,
    @SerializedName("touch") val touch: StatsTypeDevice?
)

class StatsTypeDevice(
    @Embedded(prefix = "overall") @SerializedName("overall") val overall: Overall?,
    @Embedded(prefix = "solo") @SerializedName("solo") val solo: SoloMatches?,
    @Embedded(prefix = "duo") @SerializedName("duo") val duo: DuoMatches?,
    @Embedded(prefix = "trio") @SerializedName("trio") val trio: TrioMatches?,
    @Embedded(prefix = "squad") @SerializedName("squad") val squad: TrioMatches?,
    @Embedded(prefix = "ltm") @SerializedName("ltm") val ltm: Ltm?
)

class Overall(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("top3") val top3: Int,
    @SerializedName("top5") val top5: Int,
    @SerializedName("top6") val top6: Int,
    @SerializedName("top10") val top10: Int,
    @SerializedName("top12") val top12: Int,
    @SerializedName("top25") val top25: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
) {
     operator fun minus(overall: Overall?): Overall? {
        return if (overall == null && this.matches >= 1) {
            this
        } else if (overall?.matches ?: 0 == this.matches) {
            null
        } else if (overall != null && this.matches >= 1) {
            Overall(
                score = this.score - overall.score,
                scorePerMin = this.scorePerMin,
                scorePerMatch = this.scorePerMatch,
                wins = this.wins - overall.wins,
                top3 = this.top3 - overall.top3,
                top5 = this.top5 - overall.top5,
                top6 = this.top6 - overall.top6,
                top10 = this.top10 - overall.top10,
                top12 = this.top12 - overall.top12,
                top25 = this.top25 - overall.top25,
                kills = this.kills - overall.kills,
                killsPerMin = this.killsPerMin,
                killsPerMatch = this.killsPerMatch,
                deaths = this.deaths - overall.deaths,
                kd = 0.0,
                matches = this.matches - overall.matches,
                winRate = 0.0,
                minutesPlayed = this.minutesPlayed - overall.minutesPlayed,
                playersOutlived = this.playersOutlived - overall.playersOutlived,
                lastModified = this.lastModified
            ).apply {
                kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
                winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
            }
        } else {
            return null
        }
    }

    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())

    fun getMatchesString() = matches.getStringFormat()
}

class SoloMatches(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("top10") val top10: Int,
    @SerializedName("top25") val top25: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
) {
    operator fun minus(overall: SoloMatches?): SoloMatches? {
        return if (overall == null && this.matches >= 1) {
            this
        } else if (overall?.matches ?: 0 == this.matches) {
            null
        } else if (overall != null && this.matches >= 1) {
            SoloMatches(
                score = this.score - overall.score,
                scorePerMin = this.scorePerMin,
                scorePerMatch = this.scorePerMatch,
                wins = this.wins - overall.wins,
                top10 = this.top10 - overall.top10,
                top25 = this.top25 - overall.top25,
                kills = this.kills - overall.kills,
                killsPerMin = this.killsPerMin,
                killsPerMatch = this.killsPerMatch,
                deaths = this.deaths - overall.deaths,
                kd = 0.0,
                matches = this.matches - overall.matches,
                winRate = 0.0,
                minutesPlayed = this.minutesPlayed - overall.minutesPlayed,
                playersOutlived = this.playersOutlived - overall.playersOutlived,
                lastModified = this.lastModified
            ).apply {
                kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
                winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
            }
        } else {
            return null
        }
    }

    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())
}

class DuoMatches(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("top5") val top5: Int,
    @SerializedName("top12") val top12: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
) {

    operator fun minus(duoMatches: DuoMatches?): DuoMatches? {
        return if (duoMatches == null && this.matches >= 1) {
            this
        } else if (duoMatches?.matches ?: 0 == this.matches) {
            null
        } else if (duoMatches != null && this.matches >= 1) {
            DuoMatches(
                score = this.score - duoMatches.score,
                scorePerMin = this.scorePerMin,
                scorePerMatch = this.scorePerMatch,
                wins = this.wins - duoMatches.wins,
                top5 = this.top5 - duoMatches.top5,
                top12 = this.top12 - duoMatches.top12,
                kills = this.kills - duoMatches.kills,
                killsPerMin = this.killsPerMin,
                killsPerMatch = this.killsPerMatch,
                deaths = this.deaths - duoMatches.deaths,
                kd = 0.0,
                matches = this.matches - duoMatches.matches,
                winRate = 0.0,
                minutesPlayed = this.minutesPlayed - duoMatches.minutesPlayed,
                playersOutlived = this.playersOutlived - duoMatches.playersOutlived,
                lastModified = this.lastModified
            ).apply {
                kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
                winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
            }
        } else {
            return null
        }
    }

    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())
}

class TrioMatches(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("top3") val top3: Int,
    @SerializedName("top6") val top6: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
) {

    operator fun minus(trioMatches: TrioMatches?): TrioMatches? {
        return if (trioMatches == null && this.matches >= 1) {
            this
        } else if (trioMatches?.matches ?: 0 == this.matches) {
            null
        } else if (trioMatches != null && this.matches >= 1) {
            TrioMatches(
                score = this.score - trioMatches.score,
                scorePerMin = this.scorePerMin,
                scorePerMatch = this.scorePerMatch,
                wins = this.wins - trioMatches.wins,
                top3 = this.top3 - trioMatches.top3,
                top6 = this.top6 - trioMatches.top6,
                kills = this.kills - trioMatches.kills,
                killsPerMin = this.killsPerMin,
                killsPerMatch = this.killsPerMatch,
                deaths = this.deaths - trioMatches.deaths,
                kd = 0.0,
                matches = this.matches - trioMatches.matches,
                winRate = 0.0,
                minutesPlayed = this.minutesPlayed - trioMatches.minutesPlayed,
                playersOutlived = this.playersOutlived - trioMatches.playersOutlived,
                lastModified = this.lastModified
            ).apply {
                kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
                winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
            }
        } else {
            return null
        }
    }

    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())
}

class Ltm(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
) {

    operator fun minus(ltm: Ltm?): Ltm? {
        return if (ltm == null && this.matches >= 1) {
            this
        } else if (ltm?.matches ?: 0 == this.matches) {
            null
        } else if (ltm != null && this.matches >= 1) {
            Ltm(
                score = this.score - ltm.score,
                scorePerMin = this.scorePerMin,
                scorePerMatch = this.scorePerMatch,
                wins = this.wins - ltm.wins,
                kills = this.kills - ltm.kills,
                killsPerMin = this.killsPerMin,
                killsPerMatch = this.killsPerMatch,
                deaths = this.deaths - ltm.deaths,
                kd = 0.0,
                matches = this.matches - ltm.matches,
                winRate = 0.0,
                minutesPlayed = this.minutesPlayed - ltm.minutesPlayed,
                playersOutlived = this.playersOutlived - ltm.playersOutlived,
                lastModified = this.lastModified
            ).apply {
                kd = TextUtils.getAverage(kills.toDouble(), deaths.toDouble())
                winRate = TextUtils.getAveragePercent(wins.toDouble(), matches.toDouble())
            }
        } else {
            return null
        }
    }

    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())
}