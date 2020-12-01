package robin.vitalij.fortniteassitant.model.enums

import android.content.Context
import robin.vitalij.fortniteassitant.R

enum class BattlesType {
    OVERALL {
        override fun getTitleRes() = R.string.overall_battles
    },
    SOLO {
        override fun getTitleRes() = R.string.solo_battles
    },
    DUO {
        override fun getTitleRes() = R.string.duo_battles
    },
    TRIO {
        override fun getTitleRes() = R.string.trio_battles
    },
    SQUAD {
        override fun getTitleRes() = R.string.squad_battles
    },
    LTM {
        override fun getTitleRes() = R.string.ltm_battles
    };

    abstract fun getTitleRes(): Int

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