package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.shop.ShopResponse
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

private const val SIX_THING = 6

class InventoryMapper(
    private val resourceProvider: ResourceProvider
) :
    Mapper<ShopResponse, List<CurrentShopImpl>> {

    override fun transform(obj: ShopResponse): List<CurrentShopImpl> {
        val list = arrayListOf<CurrentShopImpl>()

        if (obj.featured.isNotEmpty()) {
            list.add(
                if (obj.featured.size > SIX_THING)
                    CurrentShopTwoContentModel(resourceProvider.getString(R.string.featured), obj.featured)
                else CurrentShopOneColumnModel(resourceProvider.getString(R.string.featured), obj.featured)
            )
        }

        if (obj.daily.isNotEmpty()) {
            list.add(
                if (obj.daily.size > SIX_THING)
                    CurrentShopTwoContentModel(resourceProvider.getString(R.string.daily), obj.daily)
                else CurrentShopOneColumnModel(resourceProvider.getString(R.string.daily), obj.daily)
            )
        }

        if (obj.specialDaily.isNotEmpty()) {
            list.add(
                if (obj.specialDaily.size > SIX_THING)
                    CurrentShopTwoContentModel(resourceProvider.getString(R.string.special_daily), obj.specialDaily)
                else CurrentShopOneColumnModel(resourceProvider.getString(R.string.special_daily), obj.specialDaily)
            )
        }

        if(obj.specialFeatured.isNotEmpty()) {
            list.add(CurrentShopTitleModel(resourceProvider.getString(R.string.special_featured)))
            obj.specialFeatured.forEach {
                list.add(CurrentShopItemModel(it))
            }
        }

        return list
    }
}