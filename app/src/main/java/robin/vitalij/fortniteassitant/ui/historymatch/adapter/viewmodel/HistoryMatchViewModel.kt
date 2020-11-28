package robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel

import robin.vitalij.fortniteassitant.db.entity.MatchEntity

class HistoryMatchViewModel(val match: MatchEntity): HistoryMatch {

    override fun getType() = HistoryMatchType.MATCHES

}