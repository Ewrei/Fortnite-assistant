package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.battlepassreward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.SeasonStatsFish
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

fun List<SeasonStatsFish>.getSeason(resourceProvider: ResourceProvider): List<SeasonModel> {
    val list = arrayListOf<SeasonModel>()
    this.forEach {
        list.add(SeasonModel(it.season, resourceProvider.getString(R.string.season_format, it.season.toString())))
    }
    return list
}