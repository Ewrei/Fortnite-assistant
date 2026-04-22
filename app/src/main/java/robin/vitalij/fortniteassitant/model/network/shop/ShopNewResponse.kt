package robin.vitalij.fortniteassitant.model.network.shop

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

class ShopAdapterItem(
    val section: LayoutModel,
    val shops: List<ShopEntry>
)

// Корневой ответ
class ShopNewResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: ShopData
)

class ShopData(
    @SerializedName("hash") val hash: String,
    @SerializedName("date") val date: String,
    @SerializedName("vbuckIcon") val vbuckIcon: String,
    @SerializedName("entries") val entries: List<ShopEntry>
)

class ShopEntry(
    @SerializedName("regularPrice") val regularPrice: Int,
    @SerializedName("finalPrice") val finalPrice: Int,
    @SerializedName("devName") val devName: String,
    @SerializedName("offerId") val offerId: String,
    @SerializedName("inDate") val inDate: String,
    @SerializedName("outDate") val outDate: String,
    @SerializedName("offerTag") val offerTag: OfferTagModel?,
    @SerializedName("bundle") val bundle: BundleModel?,
    @SerializedName("banner") val banner: BannerModel?,
    @SerializedName("giftable") val giftable: Boolean,
    @SerializedName("refundable") val refundable: Boolean,
    @SerializedName("sortPriority") val sortPriority: Int,
    @SerializedName("layoutId") val layoutId: String?,
    @SerializedName("layout") val layout: LayoutModel?,
    @SerializedName("colors") val colors: ColorsModel?,
    @SerializedName("tileSize") val tileSize: String,
    @SerializedName("newDisplayAssetPath") val newDisplayAssetPath: String?,
    @SerializedName("displayAssetPath") val displayAssetPath: String?,
    @SerializedName("newDisplayAsset") val newDisplayAsset: NewDisplayAssetModel?,
    @SerializedName("brItems") val brItems: List<BrItemModel>?,
    @SerializedName("tracks") val tracks: List<TrackModel>?,
    @SerializedName("cars") val cars: List<CarModel>?,
    @SerializedName("instruments") val instruments: List<InstrumentModel>?
) : Serializable

class BundleModel(
    @SerializedName("name") val name: String,
    @SerializedName("info") val info: String,
    @SerializedName("image") val image: String
) : Serializable

class BannerModel(
    @SerializedName("value") val value: String,
    @SerializedName("intensity") val intensity: String,
    @SerializedName("backendValue") val backendValue: String
) : Serializable

class OfferTagModel(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String
) : Serializable

class LayoutModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("index") val index: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("showIneligibleOffers") val showIneligibleOffers: String,
    @SerializedName("useWidePreview") val useWidePreview: Boolean,
    @SerializedName("displayType") val displayType: String,
    @SerializedName("category") val category: String?
) : Serializable

class ColorsModel(
    @SerializedName("color1") val color1: String?,
    @SerializedName("color2") val color2: String?,
    @SerializedName("color3") val color3: String?,
    @SerializedName("textBackgroundColor") val textBackgroundColor: String?
) : Serializable

class NewDisplayAssetModel(
    @SerializedName("id") val id: String,
    @SerializedName("cosmeticId") val cosmeticId: String?,
    @SerializedName("materialInstances") val materialInstances: List<Any>,
    @SerializedName("renderImages") val renderImages: List<RenderImageModel>?
) : Serializable

class RenderImageModel(
    @SerializedName("productTag") val productTag: String,
    @SerializedName("fileName") val fileName: String,
    @SerializedName("image") val image: String
) : Serializable

class BrItemModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: TypeModel,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("series") val series: SeriesModel?,
    @SerializedName("set") val set: SetModel?,
    @SerializedName("introduction") val introduction: IntroductionModel?,
    @SerializedName("images") val images: BrItemImages,
    @SerializedName("variants") val variants: List<VariantModel>?,
    @SerializedName("metaTags") val metaTags: List<String>?,
    @SerializedName("showcaseVideo") val showcaseVideo: String?,
    @SerializedName("dynamicPakId") val dynamicPakId: String?,
    @SerializedName("added") val added: String
) : Serializable

class TypeModel(
    @SerializedName("value") val value: String,
    @SerializedName("displayValue") val displayValue: String,
    @SerializedName("backendValue") val backendValue: String
) : Serializable

class RarityModel(
    @SerializedName("value") val value: String,
    @SerializedName("displayValue") val displayValue: String,
    @SerializedName("backendValue") val backendValue: String
) : Serializable

class SeriesModel(
    @SerializedName("value") val value: String,
    @SerializedName("image") val image: String?,
    @SerializedName("colors") val colors: List<String>?,
    @SerializedName("backendValue") val backendValue: String
) : Serializable

class SetModel(
    @SerializedName("value") val value: String,
    @SerializedName("text") val text: String,
    @SerializedName("backendValue") val backendValue: String
) : Serializable

class IntroductionModel(
    @SerializedName("chapter") val chapter: String,
    @SerializedName("season") val season: String,
    @SerializedName("text") val text: String,
    @SerializedName("backendValue") val backendValue: Int
) : Serializable

class BrItemImages(
    @SerializedName("smallIcon") val smallIcon: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("featured") val featured: String?,
    @SerializedName("lego") val lego: LegoImages?,
    @SerializedName("bean") val bean: BeanImages?,
    @SerializedName("other") val other: OtherImages?
) : Serializable

class LegoImages(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
) : Serializable

class BeanImages(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
) : Serializable

class OtherImages(
    @SerializedName("background") val background: String?
) : Serializable

class VariantModel(
    @SerializedName("channel") val channel: String,
    @SerializedName("type") val type: String?,
    @SerializedName("options") val options: List<VariantOptionModel>
) : Serializable

class VariantOptionModel(
    @SerializedName("tag") val tag: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String?
) : Serializable

class TrackModel(
    @SerializedName("id") val id: String,
    @SerializedName("devName") val devName: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("album") val album: String?,
    @SerializedName("releaseYear") val releaseYear: Int,
    @SerializedName("bpm") val bpm: Int,
    @SerializedName("duration") val duration: Int,
    @SerializedName("difficulty") val difficulty: TrackDifficultyModel,
    @SerializedName("genres") val genres: List<String>?,
    @SerializedName("albumArt") val albumArt: String,
    @SerializedName("added") val added: String
) : Serializable

class TrackDifficultyModel(
    @SerializedName("vocals") val vocals: Int,
    @SerializedName("guitar") val guitar: Int,
    @SerializedName("bass") val bass: Int,
    @SerializedName("plasticBass") val plasticBass: Int,
    @SerializedName("drums") val drums: Int,
    @SerializedName("plasticDrums") val plasticDrums: Int
) : Serializable

class CarModel(
    @SerializedName("id") val id: String,
    @SerializedName("vehicleId") val vehicleId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: TypeModel,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("series") val series: SeriesModel?,
    @SerializedName("images") val images: CarImages,
    @SerializedName("added") val added: String
) : Serializable

class CarImages(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
) : Serializable

class InstrumentModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: TypeModel,
    @SerializedName("rarity") val rarity: RarityModel,
    @SerializedName("series") val series: SeriesModel?,
    @SerializedName("images") val images: CarImages,
    @SerializedName("added") val added: String
) : Serializable