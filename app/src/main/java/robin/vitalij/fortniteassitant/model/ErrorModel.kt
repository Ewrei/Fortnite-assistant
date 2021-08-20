package robin.vitalij.fortniteassitant.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    val textResourceId: Int,
    val imageResourceId: Int,
    val descriptionResourceId: Int? = null,
    val isButtonVisible: Boolean = false,
    val errors: ErrorModel2? = null
)

data class ErrorModel2(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String
)