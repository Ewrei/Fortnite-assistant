package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel

class CosmeticsTagViewModel(
    val tags: List<String>
): Cosmetics {
    override fun getType() = CosmeticsType.TAG
}