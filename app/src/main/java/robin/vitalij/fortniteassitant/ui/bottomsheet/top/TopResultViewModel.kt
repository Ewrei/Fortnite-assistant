package robin.vitalij.fortniteassitant.ui.bottomsheet.top

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.TopListItem
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class TopResultViewModel(private val resourceProvider: ResourceProvider) : BaseViewModel() {

    lateinit var topFullModel: TopFullModel

    val mutableLiveData = MutableLiveData<List<TopListItem>>()

    fun loadData() {
        mutableLiveData.value = getTops()
    }

    private fun getTops(): List<TopListItem> {
        val list = mutableListOf<TopListItem>()
        list.add(TopListItem.HeaderItem(resourceProvider.getString(R.string.score)))
        list.add(TopListItem.ContentItem(TopType.SCORE))
        list.add(TopListItem.ContentItem(TopType.SCORE_PER_MIN))
        list.add(TopListItem.ContentItem(TopType.SCORE_PER_MATCH))

        list.add(TopListItem.HeaderItem(resourceProvider.getString(R.string.kills)))
        list.add(TopListItem.ContentItem(TopType.KILLS))
        list.add(TopListItem.ContentItem(TopType.KD))
        list.add(TopListItem.ContentItem(TopType.KILLS_PER_MIN))
        list.add(TopListItem.ContentItem(TopType.KILLS_PER_MATCH))


        list.add(TopListItem.HeaderItem(resourceProvider.getString(R.string.combat)))
        list.add(TopListItem.ContentItem(TopType.WINS))
        list.add(TopListItem.ContentItem(TopType.WINS_PERCENT))
        list.add(TopListItem.ContentItem(TopType.DEATHS))

        list.add(TopListItem.HeaderItem(resourceProvider.getString(R.string.general)))
        list.add(TopListItem.ContentItem(TopType.TIME_PLAYED))
        list.add(TopListItem.ContentItem(TopType.PLAYERS_OUTLIVED))

        return list
    }
}