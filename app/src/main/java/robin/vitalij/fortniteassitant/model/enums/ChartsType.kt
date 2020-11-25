package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class ChartsType {
    KD {
        override fun getTitleRes() = R.string.kd_full
        override fun getTitleShortRes() = R.string.kd
        override fun getProgressMax() = 1
    },
    WIN_RATE {
        override fun getTitleRes() = R.string.win_rate
        override fun getTitleShortRes() = R.string.win_rate
        override fun getProgressMax() = 100
    };

    abstract fun getTitleRes(): Int
    abstract fun getTitleShortRes(): Int
    abstract fun getProgressMax(): Int
}