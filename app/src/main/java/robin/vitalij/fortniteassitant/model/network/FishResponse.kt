package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

data class FishResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("lang") val lang: String,
    @SerializedName("fish") val fishes: List<FishModel>
)

data class FishModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("details") val details: String,
    @SerializedName("needsProFishingRod") val needsProFishingRod: Boolean,
    @SerializedName("sizeMin") val sizeMin: Int,
    @SerializedName("sizeMax") val sizeMax: Int,
    @SerializedName("heal") val heal: Int,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("maxStackSize") val maxStackSize: Int,
    @SerializedName("image") val image: String
)