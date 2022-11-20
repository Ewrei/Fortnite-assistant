package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter

import robin.vitalij.fortniteassitant.db.entity.*
import robin.vitalij.fortniteassitant.db.entity.Set

sealed class CosmeticsListItem {

    data class HeaderItem(
        val images: Images?,
        val name: String,
        val description: String?,
        val typeCosmetics: Type,
        var rarity: Rarity?,
        var introduction: String?,
        var set: Set?,
        var addDate: String?
    ) : CosmeticsListItem()

    data class TagItem(val tags: List<String>) : CosmeticsListItem()

    data class VariantItem(val variant: Variant) : CosmeticsListItem()
}