package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class FishResponse(
    @SerializedName("result") var result: Boolean,
    @SerializedName("lang") var lang: String,
    @SerializedName("fish") var fishes: List<Fish>
)

class Fish(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("details") var details: String,
    @SerializedName("needsProFishingRod") var needsProFishingRod: Boolean,
    @SerializedName("sizeMin") var sizeMin: Int,
    @SerializedName("sizeMax") var sizeMax: Int,
    @SerializedName("heal") var heal: Int,
    @SerializedName("rarity") var rarity: String,
    @SerializedName("maxStackSize") var maxStackSize: Int,
    @SerializedName("image") var image: String
)