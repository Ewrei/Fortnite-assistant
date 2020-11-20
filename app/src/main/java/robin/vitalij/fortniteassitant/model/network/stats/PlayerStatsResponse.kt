package robin.vitalij.fortniteassitant.model.network.stats

import com.google.gson.annotations.SerializedName

class PlayerStatsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val playerStatsData: PlayerStatsData,
)

class PlayerStatsData(
    @SerializedName("image") val image: String?,
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
    @SerializedName("overall") val overall: Overall?,
    @SerializedName("solo") val solo: Matches?,
    @SerializedName("duo") val duo: Matches?,
    @SerializedName("trio") val trio: Matches?,
    @SerializedName("squad") val squad: Matches?,
    @SerializedName("ltm") val ltm: Ltm?
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
    @SerializedName("kd") val kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") val winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
)

class Matches(
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
    @SerializedName("kd") val kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") val winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
)

class Ltm(
    @SerializedName("score") val score: Long,
    @SerializedName("scorePerMin") val scorePerMin: Double,
    @SerializedName("scorePerMatch") val scorePerMatch: Double,
    @SerializedName("wins") val wins: Int,
    @SerializedName("kills") val kills: Int,
    @SerializedName("killsPerMin") val killsPerMin: Double,
    @SerializedName("killsPerMatch") val killsPerMatch: Double,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("kd") val kd: Double,
    @SerializedName("matches") val matches: Int,
    @SerializedName("winRate") val winRate: Double,
    @SerializedName("minutesPlayed") val minutesPlayed: Int,
    @SerializedName("playersOutlived") val playersOutlived: Long,
    @SerializedName("lastModified") val lastModified: String
)