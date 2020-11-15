package robin.vitalij.fortniteassitant.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    val textResourceId: Int,
    val imageResourceId: Int,
    val descriptionResourceId: Int? = null,
    val isButtonVisible: Boolean = false,
    val errors: List<ErrorModel2> = arrayListOf()
)

data class ErrorModel2(
    @SerializedName("message") var message: String
)