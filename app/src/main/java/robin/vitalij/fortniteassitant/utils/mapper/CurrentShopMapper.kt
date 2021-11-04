package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.network.shop.SectionModel
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper


class CurrentShopMapper : Mapper<ShopNewResponse, List<ShopAdapterItem>> {

    override fun transform(obj: ShopNewResponse): List<ShopAdapterItem> {
        val resultLinked = LinkedHashMap<SectionModel, MutableList<ShopNewItem>>()

        obj.shops.forEach { shopItem ->
            resultLinked[shopItem.section]?.add(shopItem) ?: run {
                resultLinked[shopItem.section] = mutableListOf()
                resultLinked[shopItem.section]?.add(
                    shopItem
                )
            }
        }

        val result = mutableListOf<ShopAdapterItem>()
        resultLinked.forEach {
            result.add(ShopAdapterItem(it.key, it.value))
        }


        return result
    }
}