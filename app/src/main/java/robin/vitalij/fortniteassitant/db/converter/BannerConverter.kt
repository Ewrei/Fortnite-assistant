package robin.vitalij.fortniteassitant.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import robin.vitalij.fortniteassitant.db.entity.BannerImage

class BannerConverter {

    var gson = Gson()

    @TypeConverter
    fun toTypePurse(data: String): BannerImage {

        val listType = object : TypeToken<BannerImage>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toOrdinal(list: BannerImage): String {
        return gson.toJson(list)
    }
}