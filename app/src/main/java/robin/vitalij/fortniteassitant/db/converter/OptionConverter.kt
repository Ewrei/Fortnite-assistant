package robin.vitalij.fortniteassitant.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import robin.vitalij.fortniteassitant.db.entity.Option
import robin.vitalij.fortniteassitant.db.entity.Variant
import java.util.*

class OptionConverter {

    var gson = Gson()

    @TypeConverter
    fun toTypePurse(data: String?): List<Option>? {

        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Option>?>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toOrdinal(list: List<Option>?): String? {
        return gson.toJson(list)
    }
}