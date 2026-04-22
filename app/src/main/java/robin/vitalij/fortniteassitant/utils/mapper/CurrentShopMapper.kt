package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopEntry
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper


class CurrentShopMapper : Mapper<ShopNewResponse, List<ShopAdapterItem>> {

    override fun transform(obj: ShopNewResponse): List<ShopAdapterItem> {

        val resultLinked = LinkedHashMap<String, MutableList<ShopEntry>>()

        obj.data.entries.sortedByDescending { it.layout?.rank }.forEach { shopItem ->
            resultLinked[shopItem.layout?.id]?.add(shopItem) ?: run {
                if (shopItem.layout != null) {
                    resultLinked[shopItem.layout.id] = mutableListOf()
                    resultLinked[shopItem.layout.id]?.add(
                        shopItem
                    )
                }
            }
        }

        val result = mutableListOf<ShopAdapterItem>()
        resultLinked.forEach {
            if (it.value.first().layout != null) {
                result.add(
                    ShopAdapterItem(
                    it.value.first().layout!!, it.value.sortedWith(
                            compareByDescending<ShopEntry> {
                        it.layoutId?.substringAfterLast(".")?.toIntOrNull() ?: 0
                    }.thenByDescending {
                        it.sortPriority
                    }
                )))
            }
        }

        return result
    }
}