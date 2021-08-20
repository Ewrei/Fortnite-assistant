package robin.vitalij.fortniteassitant.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import robin.vitalij.fortniteassitant.db.entity.BannerImage

class BannerConverter {

    private val gson = Gson()

    @TypeConverter
    fun toTypePurse(data: String): BannerImage =
        gson.fromJson(data, object : TypeToken<BannerImage>() {}.type)

    @TypeConverter
    fun toOrdinal(list: BannerImage) = gson.toJson(list)
}