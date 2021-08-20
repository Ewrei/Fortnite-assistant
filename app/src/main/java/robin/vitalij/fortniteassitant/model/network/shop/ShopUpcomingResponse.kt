package robin.vitalij.fortniteassitant.model.network.shop

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShopUpcomingResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("lastUpdate") val lastUpdate: LastUpdate,
    @SerializedName("items") val items: List<ItemShopUpcoming>
)

class LastUpdate(
    @SerializedName("date") val date: String,
    @SerializedName("uid") val uid: String
)

class ItemShopUpcoming(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("set") val set: String,
    @SerializedName("price") val price: Int,
    @SerializedName("interest") val interest: Double,
    @SerializedName("releaseDate") val releaseDate: Any?,
    @SerializedName("description") val description: String,
    @SerializedName("reactive") val reactive: Boolean,
    @SerializedName("images") val images: OtherImage
): Serializable