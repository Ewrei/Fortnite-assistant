package robin.vitalij.fortniteassitant.model.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import robin.vitalij.fortniteassitant.db.entity.Series

class CrewModel(
    @SerializedName("type") var type: String,
    @SerializedName("date") var date: String,
    @SerializedName("video") var video: String?,
    @SerializedName("skinImageTakeOver") var skinImageTakeOver: Boolean,
    @SerializedName("descriptions") var descriptions: DescriptionsModel,
    @SerializedName("images") var images: CrewImageModel,
    @SerializedName("rewards") var rewards: List<CrewRewardsModel>
)

class DescriptionsModel(
    @SerializedName("title") var title: String,
    @SerializedName("battlepass") var battlepass: String,
    @SerializedName("vbucksTitle") var vbucksTitle: String
)

class CrewImageModel(
    @SerializedName("battlepass") var battlepass: String,
    @SerializedName("battlepassTile") var battlepassTile: String?,
    @SerializedName("itemShopTile") var itemShopTile: String,
    @SerializedName("skin") var skin: String,
    @SerializedName("apiRender") var apiRender: String,
)

@Parcelize
class CrewRewardsModel(
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("item") var item: CrewItemModel
) : Parcelable

@Parcelize
class CrewItemModel(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("type") var type: CrewItemTypeModel,
    @SerializedName("rarity") var rarity: CrewItemRarityModel?,
    @SerializedName("series") var series: Series?,
    @SerializedName("images") var images: CrewItemImageModel?,
) : Parcelable

@Parcelize
class CrewItemRarityModel(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String
) : Parcelable

@Parcelize
class CrewItemImageModel(
    @SerializedName("icon") var icon: String?,
    @SerializedName("featured") var featured: String?,
    @SerializedName("background") var background: String?,
    @SerializedName("full_background") var fullBackground: String?
) : Parcelable

@Parcelize
class CrewItemTypeModel(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String
) : Parcelable