package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder

import android.graphics.Paint
import kotlinx.android.synthetic.main.item_inventory_content.view.*
import robin.vitalij.fortniteassitant.databinding.ItemInventoryContentBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopItemModel

class CurrentShopItemHolder(
    override var binding: ItemInventoryContentBinding,
    private val onClick: (shopItem: ShopItem) -> Unit
) : BaseViewHolder<CurrentShopImpl>(binding) {

    override fun bind(item: CurrentShopImpl) {
        (item as? CurrentShopItemModel)?.let {
            binding.item = item.shopItem
            itemView.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.setOnClickListener {
                onClick(item.shopItem)
            }
        }
    }
}