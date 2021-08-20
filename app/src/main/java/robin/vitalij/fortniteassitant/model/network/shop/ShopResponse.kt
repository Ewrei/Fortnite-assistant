package robin.vitalij.fortniteassitant.model.network.shop

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShopResponse(
    @SerializedName("endingDates") val endingDates: EndingDates,
    @SerializedName("featured") val featured: List<ShopItem>,
    @SerializedName("daily") val daily: List<ShopItem>,
    @SerializedName("specialFeatured") val specialFeatured: List<ShopItem>,
    @SerializedName("specialDaily") val specialDaily: List<ShopItem>,
    @SerializedName("community") val community: List<ShopItem>,
    @SerializedName("offers") val offers: List<ShopItem>
)


class EndingDates(
    @SerializedName("daily") val daily: String,
    @SerializedName("featured") val featured: String,
)

class ShopItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("internalRarity") val internalRarity: String,
    @SerializedName("price") val price: Int,
    @SerializedName("priceNoDiscount") val priceNoDiscount: Int,
    @SerializedName("categories") val categories: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("banner") val banner: String,
    @SerializedName("offer") val offer: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("lastAppearance") val lastAppearance: String,
    @SerializedName("interest") val interest: Double,
    @SerializedName("giftAllowed") val giftAllowed: Boolean,
    @SerializedName("buyAllowed") val buyAllowed: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("full_background") val fullBackground: String,
    @SerializedName("items") val items: List<String>,
    @SerializedName("otherItemsDetails") val otherItemsDetails: List<OtherItemsDetails>
): Serializable

class OtherItemsDetails(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("internalRarity") val internalRarity: String,
    @SerializedName("images") val images: OtherImage
): Serializable

class OtherImage(
    @SerializedName("icon") val icon: String?,
    @SerializedName("featured") val featured: String?,
    @SerializedName("background") val background: String?,
    @SerializedName("full_background") val fullBackground: String?
): Serializable

class CustomColors(
    @SerializedName("background") val background: String,
    @SerializedName("gradiant") val gradiant: Gradiant
)

class Gradiant(
    @SerializedName("start") val start: String,
    @SerializedName("stop") val stop: String
)