package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder

import kotlinx.android.synthetic.main.item_inventory_two_column.view.*
import robin.vitalij.fortniteassitant.databinding.ItemInventoryTwoColumnBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.adapter.InventoryContentAdapter
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopTwoContentModel

class CurrentShopTwoColumnsHolder(
    override var binding: ItemInventoryTwoColumnBinding,
    private val onClick: (inventoryModel: ShopItem) -> Unit,
    private val widthPixels: Int
) : BaseViewHolder<CurrentShopImpl>(binding) {

    override fun bind(item: CurrentShopImpl) {
        (item as? CurrentShopTwoContentModel)?.let {
            binding.item = item
            itemView.recyclerView.run {
                adapter = initInventoryContentAdapter(item.inventoryModels)
            }
        }
    }

    private fun initInventoryContentAdapter(list: List<ShopItem>): InventoryContentAdapter {
        val adapter = InventoryContentAdapter(
            onClick,
            widthPixels
        )
        if (list.isNotEmpty()) {
            adapter.setData(list)
        }
        return adapter
    }
}