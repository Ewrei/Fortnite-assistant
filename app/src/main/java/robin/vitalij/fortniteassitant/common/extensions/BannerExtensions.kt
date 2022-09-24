package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.network.BannerModel

fun List<BannerModel>.convertToBannerEntities(): List<BannerEntity> {
    return this.map {
        BannerEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            devName = it.devName,
            category = it.category ?: "",
            fullUsageRights = it.fullUsageRights,
            bannerImage = it.bannerImage
        )
    }
}