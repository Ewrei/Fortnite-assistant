package robin.vitalij.fortniteassitant.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import robin.vitalij.fortniteassitant.db.entity.Variant
import java.util.*

class StringConverter {

    var gson = Gson()

    @TypeConverter
    fun toTypePurse(data: String?): List<String>? {

        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<String>?>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toOrdinal(list: List<String>?): String? {
        return gson.toJson(list)
    }
}