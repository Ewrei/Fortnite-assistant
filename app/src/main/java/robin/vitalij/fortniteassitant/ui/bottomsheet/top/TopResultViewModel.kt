package robin.vitalij.fortniteassitant.ui.bottomsheet.top

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopContentModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopHeaderModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class TopResultViewModel(private val resourceProvider: ResourceProvider) : BaseViewModel() {

    lateinit var topFullModel: TopFullModel

    val mutableLiveData = MutableLiveData<List<TopResult>>()

    fun loadData() {
        mutableLiveData.value = getTops()
    }

    private fun getTops(): List<TopResult> {
        val list = arrayListOf<TopResult>()
        list.add(TopHeaderModel(resourceProvider.getString(R.string.score)))
        list.add(TopContentModel(TopType.SCORE))
        list.add(TopContentModel(TopType.SCORE_PER_MIN))
        list.add(TopContentModel(TopType.SCORE_PER_MATCH))

        list.add(TopHeaderModel(resourceProvider.getString(R.string.kills)))
        list.add(TopContentModel(TopType.KILLS))
        list.add(TopContentModel(TopType.KD))
        list.add(TopContentModel(TopType.KILLS_PER_MIN))
        list.add(TopContentModel(TopType.KILLS_PER_MATCH))


        list.add(TopHeaderModel(resourceProvider.getString(R.string.combat)))
        list.add(TopContentModel(TopType.WINS))
        list.add(TopContentModel(TopType.WINS_PERCENT))
        list.add(TopContentModel(TopType.DEATHS))

        list.add(TopHeaderModel(resourceProvider.getString(R.string.general)))
        list.add(TopContentModel(TopType.TIME_PLAYED))
        list.add(TopContentModel(TopType.PLAYERS_OUTLIVED))

        return list
    }
}