package robin.vitalij.fortniteassitant.model.network.stats

import com.google.gson.annotations.SerializedName

class PlayerMatchesResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("account") val account: String,
    @SerializedName("name") val name: String,
    @SerializedName("max_results") val maxResults: Int,
    @SerializedName("matches") val matches: List<MatchesPlayer>
)

class MatchesPlayer(
    @SerializedName("platform") val platform: String,
    @SerializedName("date") val date: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("readable_name") val readableName: String,
    @SerializedName("kills") val kills: Int,
    @SerializedName("matchesplayed") val matchesplayed: Int,
    @SerializedName("minutesplayed") val minutesplayed: Int,
    @SerializedName("playersoutlived") val playersoutlived: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("placetop1") val placetop1: Int,
    @SerializedName("placetop3") val placetop3: Int,
    @SerializedName("placetop5") val placetop5: Int,
    @SerializedName("placetop6") val placetop6: Int,
    @SerializedName("placetop10") val placetop10: Int,
    @SerializedName("placetop12") val placetop12: Int,
    @SerializedName("placetop25") val placetop25: Int
)