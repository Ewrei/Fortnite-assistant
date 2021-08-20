package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

class CosmeticsNewResponse(
    @SerializedName("result") var result: Boolean,
    @SerializedName("data") var data: Data
)

class Data(
    @SerializedName("lastAddition") var lastAddition: String,
    @SerializedName("items") var items: List<CosmeticsNewEntity>
)