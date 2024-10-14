package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity

data class BannerResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("data") val data: List<BannerModel>
)