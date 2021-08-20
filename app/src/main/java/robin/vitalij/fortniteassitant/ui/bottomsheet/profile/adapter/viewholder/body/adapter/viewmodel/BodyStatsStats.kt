package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel

class BodyStatsStats(
    val title: String,
    val score: Long,
    val wins: Int,
    val kills: Int,
    val killsPerMin: Double,
    val killsPerMatch: Double,
    val deaths: Int,
    val kd: Double,
    val matches: Int,
    val winRate: Double,
    val minutesPlayed: Int,
    val titleOne: String,
    val valueOne: String,
    val titleTwo: String,
    val valueTwo: String
) : BodyStats {
    override fun getType() = BodyStatsType.STATS
}