package robin.vitalij.fortniteassitant.model.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import robin.vitalij.fortniteassitant.db.entity.Series

data class CrewModel(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("video") val video: String?,
    @SerializedName("skinImageTakeOver") val skinImageTakeOver: Boolean,
    @SerializedName("descriptions") val descriptions: DescriptionsModel,
    @SerializedName("images") val images: CrewImageModel,
    @SerializedName("rewards") val rewards: List<CrewRewardsModel>
)

data class DescriptionsModel(
    @SerializedName("title") val title: String,
    @SerializedName("battlepass") val battlepass: String,
    @SerializedName("vbucksTitle") val vbucksTitle: String
)

data class CrewImageModel(
    @SerializedName("battlepass") val battlepass: String,
    @SerializedName("battlepassTile") val battlepassTile: String?,
    @SerializedName("itemShopTile") val itemShopTile: String,
    @SerializedName("skin") val skin: String,
    @SerializedName("apiRender") val apiRender: String,
)

@Parcelize
data class CrewRewardsModel(
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("item") val item: CrewItemModel
) : Parcelable

@Parcelize
data class CrewItemModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("type") val type: CrewItemTypeModel,
    @SerializedName("rarity") val rarity: CrewItemRarityModel?,
    @SerializedName("series") val series: Series?,
    @SerializedName("images") val images: CrewItemImageModel?,
) : Parcelable

@Parcelize
data class CrewItemRarityModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
) : Parcelable

@Parcelize
data class CrewItemImageModel(
    @SerializedName("icon") val icon: String?,
    @SerializedName("featured") val featured: String?,
    @SerializedName("background") val background: String?,
    @SerializedName("full_background") val fullBackground: String?
) : Parcelable

@Parcelize
data class CrewItemTypeModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
) : Parcelable