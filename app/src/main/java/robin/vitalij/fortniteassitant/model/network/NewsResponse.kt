package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.common.extensions.DATE_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateFull

data class NewsResponse(
    @SerializedName("type") val type: String,
    @SerializedName("news") val news: List<NewsModel>
)

data class NewsModel(
    @SerializedName("title") val title: String,
    @SerializedName("tabTitle") val tabTitle: String,
    @SerializedName("date") val date: String,
    @SerializedName("body") val body: String,
    @SerializedName("image") val image: String,
    @SerializedName("video") val video: String?
) {
    fun getDateString() = date.getDateFull(DATE_FULL)
}