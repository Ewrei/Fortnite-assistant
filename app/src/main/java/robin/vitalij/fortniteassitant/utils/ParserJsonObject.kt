package robin.vitalij.fortniteassitant.utils

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import robin.vitalij.fortniteassitant.model.ErrorModel2

object ParserJsonObject {

    fun getErrors(getResponse: String?): List<ErrorModel2> {
        val list = arrayListOf<ErrorModel2>()

        try {
            val jsonArray = JSONArray(getResponse)
            for (i in 0 until jsonArray.length()) {
                val jsonObject1: JSONObject = jsonArray.getJSONObject(i)
                list.add(Gson().fromJson(jsonObject1.toString(), ErrorModel2::class.java))
            }
            return list

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }
}