package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.model.network.shop.OtherImage
import java.io.Serializable

data class BattlePassRewardsResponse (
    @SerializedName("result") val result: Boolean,
    @SerializedName("season") val season: Int,
    @SerializedName("paid") val paid: Paid,
    @SerializedName("free") val free: Paid
)

data class Paid(
    @SerializedName("rewards") val rewards: List<Reward>
)

data class Reward(
    @SerializedName("id") val id: String,
    @SerializedName("tier") val tier: Int,
    @SerializedName("type") val type: String,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("video") val video: Any?,
    @SerializedName("images") val images: OtherImage,
): Serializable