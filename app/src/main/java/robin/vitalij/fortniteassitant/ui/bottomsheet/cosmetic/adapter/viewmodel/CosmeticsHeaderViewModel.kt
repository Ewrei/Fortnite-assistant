package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel

import robin.vitalij.fortniteassitant.db.entity.Images
import robin.vitalij.fortniteassitant.db.entity.Rarity
import robin.vitalij.fortniteassitant.db.entity.Set
import robin.vitalij.fortniteassitant.db.entity.Type

class CosmeticsHeaderViewModel(
    val images: Images?,
    val name: String,
    val description: String?,
    val typeCosmetics: Type,
    var rarity: Rarity?,
    var introduction: String?,
    var set: Set?,
    var addDate: String?
): Cosmetics {
    override fun getType() = CosmeticsType.HEADER
}