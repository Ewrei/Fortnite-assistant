package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class FishStatsResponse(
    @SerializedName("result") val result: Boolean = false,
    @SerializedName("lang") val lang: String = "",
    @SerializedName("stats") val seasonStats: List<SeasonStatsFishModel> = arrayListOf()
)

class SeasonStatsFishModel(
    @SerializedName("season") val season: Int,
    @SerializedName("fish") val fish: List<FishStatsModel>
)

class FishStatsModel(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("length") val length: Double
)