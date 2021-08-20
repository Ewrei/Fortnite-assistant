package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class TopUser(
//    @SerializedName("playerId") var playerId: String,
    @SerializedName("userName") var userName: String?,
//    @SerializedName("timePlayed") var timePlayed: Long,
    @SerializedName("accountId") var accountId: String,
    @SerializedName("score") var score: Long,
    @SerializedName("scorePerMin") var scorePerMin: Double,
    @SerializedName("scorePerMatch") var scorePerMatch: Double,
    @SerializedName("wins") var wins: Int,
    @SerializedName("top3") var top3: Int,
    @SerializedName("top5") var top5: Int,
    @SerializedName("top6") var top6: Int,
    @SerializedName("top10") var top10: Int,
    @SerializedName("top12") var top12: Int,
    @SerializedName("top25") var top25: Int,
    @SerializedName("kills") var kills: Int,
    @SerializedName("killsPerMin") var killsPerMin: Double,
    @SerializedName("killsPerMatch") var killsPerMatch: Double,
    @SerializedName("deaths") var deaths: Int,
    @SerializedName("kd") var kd: Double,
    @SerializedName("matches") var matches: Int,
    @SerializedName("winRate") var winRate: Double,
    @SerializedName("minutesPlayed") var minutesPlayed: Int,
    @SerializedName("playersOutlived") var playersOutlived: Int
)