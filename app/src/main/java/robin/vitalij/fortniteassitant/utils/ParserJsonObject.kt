package robin.vitalij.fortniteassitant.utils

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import robin.vitalij.fortniteassitant.model.ErrorModel2

object ParserJsonObject {

    fun getError(getResponse: String?): ErrorModel2? {
        try {
            return (Gson().fromJson(getResponse.toString(), ErrorModel2::class.java))

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }
}