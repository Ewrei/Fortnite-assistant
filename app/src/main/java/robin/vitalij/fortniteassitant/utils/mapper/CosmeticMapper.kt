package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateZFull
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsHeaderViewModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsTagViewModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsVariantViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class CosmeticMapper(private val resourceProvider: ResourceProvider) :
    Mapper<CosmeticsEntity, List<Cosmetics>> {

    override fun transform(obj: CosmeticsEntity): List<Cosmetics> {
        val list = arrayListOf<Cosmetics>()

        list.add(
            CosmeticsHeaderViewModel(
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
            list.add(CosmeticsVariantViewModel(it))
        }

        obj.gameplayTags?.let {
            list.add(CosmeticsTagViewModel(it))
        }
        return list
    }
}