package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.dao.CosmeticsDao
import robin.vitalij.fortniteassitant.db.dao.CosmeticsNewDao
import robin.vitalij.fortniteassitant.db.entity.*
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.utils.mapper.CosmeticMapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class CosmeticsResultRepository @Inject constructor(
    private val cosmeticsDao: CosmeticsDao,
    private val cosmeticsNewDao: CosmeticsNewDao,
    private val resourceProvider: ResourceProvider
) {

    fun getCosmetics(cosmeticsId: String, isCosmeticsNew: Boolean): Single<List<Cosmetics>> {
        if (isCosmeticsNew) {
            return cosmeticsNewDao.getCosmetic(cosmeticsId).flatMap {
                return@flatMap Single.just(
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
            }
        } else {
            return cosmeticsDao.getCosmetic(cosmeticsId).flatMap {
                return@flatMap Single.just(CosmeticMapper(resourceProvider).transform(it))
            }
        }
    }
}
