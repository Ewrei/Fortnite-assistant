package robin.vitalij.fortniteassitant.ui.session.viewpager

import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class AdapterSessionViewModel() : BaseViewModel() {

    var detailsStatistics: ArrayList<DetailStatisticsModel> = arrayListOf()

    var sessionId: Long = 0

    var sessionLastId: Long = 0

}