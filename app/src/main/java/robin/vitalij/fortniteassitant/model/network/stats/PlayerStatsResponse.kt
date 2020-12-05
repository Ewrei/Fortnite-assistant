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
    fun getAvgScore(): Double = TextUtils.getAverage(score.toDouble(), matches.toDouble())
}