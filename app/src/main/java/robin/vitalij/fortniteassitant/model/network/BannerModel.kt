package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.BannerImage

class BannerModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("devName") val devName: String,
    @SerializedName("category") val category: String?,
    @SerializedName("fullUsageRights") val fullUsageRights: Boolean,
    @SerializedName("images") val bannerImage: BannerImage
)