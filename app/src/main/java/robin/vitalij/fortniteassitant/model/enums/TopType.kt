package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class TopType(val id: String) {

    KD("kd") {
        override fun getStringRes() = R.string.kd_full
    },
    MATCHES("matches") {
        override fun getStringRes() = R.string.matches
    },
    TIME_PLAYED("minutesPlayed") {
        override fun getStringRes() = R.string.play_time_full
    },
    PLAYERS_OUTLIVED("playersOutlived") {
        override fun getStringRes() = R.string.players_outlived
    },
    SCORE("score") {
        override fun getStringRes() = R.string.score
    },
    SCORE_PER_MIN("scorePerMin") {
        override fun getStringRes() = R.string.score_per_min
    },
    SCORE_PER_MATCH("scorePerMatch") {
        override fun getStringRes() = R.string.score_per_match
    },
    KILLS("kills") {
        override fun getStringRes() = R.string.kills
    },
    KILLS_PER_MIN("killsPerMin") {
        override fun getStringRes() = R.string.kills_per_min
    },
    KILLS_PER_MATCH("killsPerMatch") {
        override fun getStringRes() = R.string.killsPerMatch
    },
    DEATHS("deaths") {
        override fun getStringRes() = R.string.deaths
    },
    WINS("wins") {
        override fun getStringRes() = R.string.wins
    },
    WINS_PERCENT("winRate") {
        override fun getStringRes() = R.string.wins_percent
    },;

    abstract fun getStringRes(): Int
}