package robin.vitalij.fortniteassitant.ui.bottomsheet.top

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class TopResultViewModel(private val resourceProvider: ResourceProvider) : BaseViewModel() {

    lateinit var topFullModel: TopFullModel

    val mutableLiveData = MutableLiveData<List<TopResult>>()

    fun loadData() {
        mutableLiveData.value = getTops()
    }

    private fun getTops(): List<TopResult> {
        val list = arrayListOf<TopResult>()
//        list.add(TopHeaderModel(resourceProvider.getString(R.string.general)))
//        list.add(TopContentModel(TopType.TIME_PLAYED))
//        list.add(TopContentModel(TopType.SCORE))
//
//        list.add(TopHeaderModel(resourceProvider.getString(R.string.combat)))
//        list.add(TopContentModel(TopType.KD))
//        list.add(TopContentModel(TopType.KR))
//        list.add(TopContentModel(TopType.KILLS))
//        list.add(TopContentModel(TopType.DEATHS))
//        list.add(TopContentModel(TopType.HEADSHOTS))
//        list.add(TopContentModel(TopType.HEADSHOTS_PERCENT))
//
//        list.add(TopHeaderModel(resourceProvider.getString(R.string.objective)))
//        list.add(TopContentModel(TopType.BOMBS_PLANED))
//        list.add(TopContentModel(TopType.BOMBS_DEFUSED))
//        list.add(TopContentModel(TopType.MVP))
//
//        list.add(TopHeaderModel(resourceProvider.getString(R.string.round)))
//        list.add(TopContentModel(TopType.WINS))
//        list.add(TopContentModel(TopType.WINS_PERCENT))
//        list.add(TopContentModel(TopType.MATCHES_PLAYED))
//        list.add(TopContentModel(TopType.ROUNDS))
//        list.add(TopContentModel(TopType.ROUNDS_WON))
        // list.add(TopContentModel(TopType.ROUNDS_PERCENTAGE))

        return list
    }
}