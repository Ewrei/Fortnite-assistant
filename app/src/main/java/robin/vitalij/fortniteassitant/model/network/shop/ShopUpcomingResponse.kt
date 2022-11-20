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
    @SerializedName("type") val type: TypeShopModel,
    @SerializedName("name") val name: String,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("interest") val interest: Double,

    @SerializedName("set") val set: SetModel?,
    @SerializedName("releaseDate") val releaseDate: Any?,
    @SerializedName("reactive") val reactive: Boolean,
    @SerializedName("images") val images: OtherImage,
    @SerializedName("added") val added: AddedModel
): Serializable

class TypeShopModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
): Serializable

class AddedModel(
    @SerializedName("date") val date: String,
    @SerializedName("version") val version: String
)