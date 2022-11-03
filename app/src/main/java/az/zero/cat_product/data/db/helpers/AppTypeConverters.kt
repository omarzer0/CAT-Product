package az.zero.cat_product.data.db.helpers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AppTypeConverters {
    @TypeConverter
    fun fromListOfStringToString(list: List<String>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun fromStringToListOfString(string: String?): List<String>? {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return string?.let { Gson().fromJson(it, listType) }
    }

}