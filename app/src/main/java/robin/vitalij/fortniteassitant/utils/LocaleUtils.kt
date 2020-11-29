package robin.vitalij.fortniteassitant.utils

import java.util.*

object LocaleUtils {

    val locale: String
        get() {
            val language = Locale.getDefault().language
            if (language == "ru")
                return "ru"
            return when (language) {
                "uk" -> "ru"
                "fr" -> "fr"
                "de" -> "de"
                "pl" -> "pl"
                else -> "en"
            }
        }
}