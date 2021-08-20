package robin.vitalij.fortniteassitant.model.enums

import android.content.Context
import robin.vitalij.fortniteassitant.R

enum class BattlesType {
    OVERALL {
        override fun getTitleRes() = R.string.overall_battles
        override fun getServer() = "overall"
    },
    SOLO {
        override fun getTitleRes() = R.string.solo_battles
        override fun getServer() = "solo"
    },
    DUO {
        override fun getTitleRes() = R.string.duo_battles
        override fun getServer() = "duo"
    },
    TRIO {
        override fun getTitleRes() = R.string.trio_battles
        override fun getServer() = "trio"
    },
    SQUAD {
        override fun getTitleRes() = R.string.squad_battles
        override fun getServer() = "squad"
    },
    LTM {
        override fun getTitleRes() = R.string.ltm_battles
        override fun getServer() = "ltm"
    };

    abstract fun getTitleRes(): Int
    abstract fun getServer(): String

    companion object {
        fun getTitles(context: Context): List<String> {
            val list = arrayListOf<String>()
            values().forEach {
                list.add(context.getString(it.getTitleRes()))
            }
            return list
        }
    }
}