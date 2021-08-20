package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.network.shop.ShopItem

class CurrentShopTwoContentModel(
    val title: String,
    val inventoryModels: List<ShopItem>
) : CurrentShopImpl {

    override fun getType() = CurrentShopType.TWO_COLUMN

}