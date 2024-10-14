package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

data class TopUser(
//    @SerializedName("playerId") var playerId: String,
    @SerializedName("userName") val userName: String?,
//    @SerializedName("timePlayed") var timePlayed: Long,
    @SerializedName("accountId") val accountId: String,
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
    @SerializedName("playersOutlived") val playersOutlived: Int
)