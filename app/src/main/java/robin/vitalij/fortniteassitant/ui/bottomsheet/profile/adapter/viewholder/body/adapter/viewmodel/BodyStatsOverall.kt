package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.network.stats.Overall

class BodyStatsOverall(
    val title: String,
    val matches: Overall
) : BodyStats {
    override fun getType() = BodyStatsType.OVERALL
}