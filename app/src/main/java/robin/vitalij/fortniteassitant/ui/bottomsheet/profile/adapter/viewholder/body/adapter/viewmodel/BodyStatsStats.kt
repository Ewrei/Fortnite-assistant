package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.network.stats.Matches

class BodyStatsStats(
    val title: String,
    val matches: Matches
) : BodyStats {
    override fun getType() = BodyStatsType.STATS
}