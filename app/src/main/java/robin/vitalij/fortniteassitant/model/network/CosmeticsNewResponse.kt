package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

class CosmeticsNewResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("data") val data: Data
)

class Data(
    @SerializedName("lastAddition") val lastAddition: String,
    @SerializedName("items") val items: List<CosmeticsNewEntity>
)