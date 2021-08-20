package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel

import robin.vitalij.fortniteassitant.db.entity.Variant

class CosmeticsVariantViewModel(
    val variant: Variant
): Cosmetics {
    override fun getType() = CosmeticsType.VARIANT
}