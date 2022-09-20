package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.network.WeaponResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class WeaponMapper : Mapper<WeaponResponse, List<WeaponEntity>> {

    override fun transform(obj: WeaponResponse): List<WeaponEntity> {
        val list = mutableListOf<WeaponEntity>()

        obj.weapons.forEach {
            list.add(
                WeaponEntity(
                    weaponId = "${it.id}_${it.rarity}",
                    id = it.id,
                    enabled = it.enabled,
                    name = it.name,
                    description = it.description ?: "",
                    rarity = it.rarity,
                    type = it.type,
                    searchTags = it.searchTags,
                    background = it.images?.background ?: it.images?.icon ?: "",
                    dmgPB = it.mainStats.dmgPB,
                    firingRate = it.mainStats.firingRate,
                    clipSize = it.mainStats.clipSize,
                    reloadTime = it.mainStats.reloadTime,
                    bulletsPerCartridge = it.mainStats.bulletsPerCartridge,
                    spread = it.mainStats.spread,
                    spreadDownsights = it.mainStats.spreadDownsights,
                    damageZoneCritical = it.mainStats.damageZoneCritical
                )
            )
        }
        return list
    }
}