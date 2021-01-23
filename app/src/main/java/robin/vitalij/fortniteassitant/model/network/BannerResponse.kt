package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity

class BannerResponse(
    @SerializedName("result") var result: Boolean,
    @SerializedName("data") var data: List<BannerEntity>
)