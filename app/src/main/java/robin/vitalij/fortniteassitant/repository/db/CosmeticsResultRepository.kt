package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.CosmeticsDao
import robin.vitalij.fortniteassitant.db.dao.CosmeticsNewDao
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.CosmeticMapper
import javax.inject.Inject

class CosmeticsResultRepository @Inject constructor(
    private val cosmeticsDao: CosmeticsDao,
    private val cosmeticsNewDao: CosmeticsNewDao,
    private val resourceProvider: ResourceProvider
) {

    fun getCosmetics(
        cosmeticsId: String,
        isCosmeticsNew: Boolean
    ): Flow<LoadingState<List<CosmeticsListItem>>> {
        return if (isCosmeticsNew) {
            getCosmeticsNewLocale(cosmeticsId)
        } else {
            getCosmeticsLocale(cosmeticsId)
        }
    }

    private fun getCosmeticsNewLocale(cosmeticsId: String): Flow<LoadingState<List<CosmeticsListItem>>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching { cosmeticsNewDao.getCosmetic(cosmeticsId) }
                .onSuccess {
                    emit(
                        LoadingState.Success(
                            CosmeticMapper(resourceProvider).transform(
                                CosmeticsEntity(
                                    id = it.id,
                                    name = it.name,
                                    description = it.description,
                                    type = it.type,
                                    rarity = it.rarity,
                                    series = it.series,
                                    set = it.set,
                                    introduction = it.introduction,
                                    images = it.images,
                                    variants = it.variants,
                                    gameplayTags = it.gameplayTags,
                                    added = it.added
                                )
                            )
                        )
                    )
                }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

    private fun getCosmeticsLocale(cosmeticsId: String): Flow<LoadingState<List<CosmeticsListItem>>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching { cosmeticsDao.getCosmetic(cosmeticsId) }
                .onSuccess {
                    emit(LoadingState.Success(CosmeticMapper(resourceProvider).transform(it)))
                }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

}