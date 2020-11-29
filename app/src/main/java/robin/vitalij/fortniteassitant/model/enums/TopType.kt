package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class TopType(val id: String) {

    KD("kd") {
        override fun getStringRes() = R.string.kd_full
    };

    abstract fun getStringRes(): Int
}