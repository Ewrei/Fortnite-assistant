package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.common.extensions.DATE_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateFull

class NewsResponse(
    @SerializedName("type") var type: String,
    @SerializedName("news") var news: List<NewsModel>
)

class NewsModel(
    @SerializedName("title") var title: String,
    @SerializedName("tabTitle") var tabTitle: String,
    @SerializedName("date") var date: String,
    @SerializedName("body") var body: String,
    @SerializedName("image") var image: String,
    @SerializedName("video") var video: String?
) {
    fun getDateString() = date.getDateFull(DATE_FULL)
}