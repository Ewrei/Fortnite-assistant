package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateZFull
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class CosmeticMapper(private val resourceProvider: ResourceProvider) :
    Mapper<CosmeticsEntity, List<CosmeticsListItem>> {

    override fun transform(obj: CosmeticsEntity): List<CosmeticsListItem> {
        val list = mutableListOf<CosmeticsListItem>()

        list.add(
            CosmeticsListItem.HeaderItem(
                images = obj.images,
                name = obj.name,
                description = obj.description,
                typeCosmetics = obj.type,
                rarity = obj.rarity,
                introduction = obj.introduction?.text,
                set = obj.set,
                addDate = resourceProvider.getString(
                    R.string.add_date_format,
                    obj.added?.getDateZFull(DATE_FULL) ?: ""
                )
            )
        )

        obj.variants?.forEach {
            list.add(CosmeticsListItem.VariantItem(it))
        }

        obj.gameplayTags?.let {
            list.add(CosmeticsListItem.TagItem(it))
        }
        return list
    }
}