package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class NewsResponse (
    //TODO Добавь сюда структуру модельки
    val status: Int,
    val data: DataModel
)

class DataModel(
    @SerializedName("br") val brrrr: Brdfdfdfd
)

class Brdfdfdfd()