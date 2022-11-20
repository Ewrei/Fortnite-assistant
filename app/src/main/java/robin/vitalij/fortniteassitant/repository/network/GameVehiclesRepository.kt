package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameVehiclesRepository @Inject constructor(private val fortniteRequestsIOApi: FortniteRequestsIOApi) {

    fun getGameCrew(): Flow<LoadingState<List<VehicleModel>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getGameVehicles(LocaleUtils.locale) }
            .onSuccess { emit(LoadingState.Success(it.vehicles)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)


}