package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.battle_pass_reward.FullBattlePassRewardModel
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.BattlesPassRewardsMapper
import javax.inject.Inject

class BattlesPassRewardRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val resourceProvider: ResourceProvider
) {

    fun getBattlesPassReward(season: String): Flow<LoadingState<FullBattlePassRewardModel>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching {
                fortniteRequestsIOApi.getBattlesPassRewards(
                    LocaleUtils.locale,
                    season
                )
            }.onSuccess {
                emit(
                    LoadingState.Success(
                        BattlesPassRewardsMapper(resourceProvider).transform(it)
                    )
                )
            }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

}