package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.network.shop.ShopItem

class CurrentShopItemModel(
    val shopItem: ShopItem
) : CurrentShopImpl {

    override fun getType() = CurrentShopType.SHOP_ITEM

}