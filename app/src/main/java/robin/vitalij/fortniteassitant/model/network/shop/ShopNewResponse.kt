package robin.vitalij.fortniteassitant.model.network.shop

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShopNewResponse (
    @SerializedName("shop") val shops: List<ShopNewItem>,
)

class ShopNewItem(
    @SerializedName("mainId") val mainId: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("displayDescription") val displayDescription: String,
    @SerializedName("displayType") val displayType: String,
    @SerializedName("mainType") val mainType: String,
    @SerializedName("offerId") val offerId: String,
    @SerializedName("displayAssets") val displayAssets: List<DisplayAssetsModel>,
    @SerializedName("firstReleaseDate") val firstReleaseDate: String,
    @SerializedName("previousReleaseDate") val previousReleaseDate: Any?,
    @SerializedName("giftAllowed") val giftAllowed: Boolean,
    @SerializedName("buyAllowed") val buyAllowed: Boolean,
    @SerializedName("price") val price: PriceModel,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("series") val series: SeriesModel?,
    @SerializedName("banner") val banner: BannerModel?,
    @SerializedName("offerTag") val offerTag: OfferTagModel?,
    @SerializedName("granted") val granted: List<GrantedModel>,
): Serializable

class DisplayAssetsModel(
    @SerializedName("displayAsset") val displayAsset: String,
    @SerializedName("materialInstance") val materialInstance: String,
    @SerializedName("url") val url: String,
    @SerializedName("flipbook") val flipbook: String?,
    @SerializedName("background") val background: String,
    @SerializedName("full_background") val fullBackground: String,
): Serializable

class PriceModel(
    @SerializedName("regularPrice") val regularPrice: Int,
    @SerializedName("finalPrice") val finalPrice: Int,
): Serializable

class RarityModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
): Serializable

class SeriesModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
): Serializable

class BannerModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
): Serializable

class OfferTagModel(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
): Serializable

class GrantedModel(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: TypeGrantedModel,
    @SerializedName("name") val name: String,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("series") val series: SeriesModel?,
    @SerializedName("description") val description: String,
    @SerializedName("images") val images: Images,
    @SerializedName("set") val set: SetModel,
    @SerializedName("video") val video: Any?,
    @SerializedName("audio") val audio: Any?,
    @SerializedName("gameplayTags") val gameplayTags: List<String>,
): Serializable

class TypeGrantedModel(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
): Serializable

class Images(
    @SerializedName("icon") val icon: String,
    @SerializedName("featured") val featured: String,
    @SerializedName("background") val background: String,
    @SerializedName("full_background") val fullBackground: String
): Serializable

class SetModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("partOf") val partOf: String
): Serializable