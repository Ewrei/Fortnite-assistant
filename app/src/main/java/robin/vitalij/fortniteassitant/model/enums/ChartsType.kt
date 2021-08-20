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
    },
    AVG_SCORE {
        override fun getTitleRes() = R.string.avg_score
        override fun getTitleShortRes() = R.string.avg_score
        override fun getProgressMax() = 1000
    },
    SCORE_PER_MATCH {
        override fun getTitleRes() = R.string.score_per_match
        override fun getTitleShortRes() = R.string.score_per_match
        override fun getProgressMax() = 1000
    },
    SCORE_PER_MIN {
        override fun getTitleRes() = R.string.score_per_min
        override fun getTitleShortRes() = R.string.score_per_min
        override fun getProgressMax() = 1000
    },
    KILLS_PER_MIN {
        override fun getTitleRes() = R.string.killsPerMin
        override fun getTitleShortRes() = R.string.killsPerMin
        override fun getProgressMax() = 1
    },
    KILLS_PER_MATCH {
        override fun getTitleRes() = R.string.killsPerMatch
        override fun getTitleShortRes() = R.string.killsPerMatch
        override fun getProgressMax() = 1
    };

    abstract fun getTitleRes(): Int
    abstract fun getTitleShortRes(): Int
    abstract fun getProgressMax(): Int
}