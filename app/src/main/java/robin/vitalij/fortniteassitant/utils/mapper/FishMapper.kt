package robin.vitalij.fortniteassitant.utils.mapper

import androidx.room.ColumnInfo
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.network.FishResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class FishMapper : Mapper<FishResponse, List<FishEntity>> {

    override fun transform(obj: FishResponse): List<FishEntity> {
        val list = mutableListOf<FishEntity>()
        obj.fishes.forEach {
            list.add(
                FishEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description ?: "",
                    details = it.details ?: "",
                    rarity = it.rarity,
                    needsProFishingRod = it.needsProFishingRod,
                    sizeMin = it.sizeMin,
                    sizeMax = it.sizeMax,
                    heal = it.heal,
                    maxStackSize = it.maxStackSize,
                    image = it.image,
                )
            )
        }
        return list
    }
}