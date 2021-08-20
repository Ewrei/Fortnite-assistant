package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class FishStatsResponse(
    @SerializedName("result") var result: Boolean = false,
    @SerializedName("lang") var lang: String = "",
    @SerializedName("stats") var seasonStats: List<SeasonStatsFish> = arrayListOf()
)

class SeasonStatsFish(
    @SerializedName("season") var season: Int,
    @SerializedName("fish") var fish: List<FishStats>
)

class FishStats(
    @SerializedName("type") var type: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("image") var image: String,
    @SerializedName("length") var length: Double
)