package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.FullBattlePassRewardModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.BattlePassRewardsResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class BattlesPassRewardsMapper(private val resourceProvider: ResourceProvider) :
    Mapper<BattlePassRewardsResponse, FullBattlePassRewardModel> {

    override fun transform(obj: BattlePassRewardsResponse): FullBattlePassRewardModel {
        val list = mutableListOf<BattlesPassRewardsModel>()

        obj.free.rewards.forEach {
            list.add(BattlesPassRewardsModel(it, true, (it.id.hashCode() + it.tier).toLong()))
        }

        obj.paid.rewards.forEach {
            list.add(BattlesPassRewardsModel(it, false, (it.id.hashCode() + it.tier).toLong()))
        }

        return FullBattlePassRewardModel(list.sortedBy { it.reward.tier }, getSeasons(obj.season))
    }

    private fun getSeasons(season: Int): List<SeasonModel> {
        var seasonId = season
        val list = mutableListOf<SeasonModel>()
        list.add(SeasonModel(seasonId, resourceProvider.getString(R.string.current_season)))

        seasonId--

        for (i in seasonId downTo 2) {
            list.add(
                SeasonModel(
                    i,
                    resourceProvider.getString(R.string.season_format, i.toString())
                )
            )
        }

        return list
    }
}