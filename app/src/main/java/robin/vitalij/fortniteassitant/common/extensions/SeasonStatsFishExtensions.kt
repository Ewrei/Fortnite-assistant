package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.SeasonStatsFishModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

fun List<SeasonStatsFishModel>.getSeason(resourceProvider: ResourceProvider): List<SeasonModel> {
    val list = mutableListOf<SeasonModel>()
    this.forEach {
        list.add(SeasonModel(it.season, resourceProvider.getString(R.string.season_format, it.season.toString())))
    }
    return list
}