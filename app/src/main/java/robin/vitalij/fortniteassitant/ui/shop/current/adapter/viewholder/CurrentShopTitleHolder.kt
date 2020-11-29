package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemInventoryTitleBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopTitleModel

class CurrentShopTitleHolder(
    override var binding: ItemInventoryTitleBinding
) : BaseViewHolder<CurrentShopImpl>(binding) {

    override fun bind(item: CurrentShopImpl) {
        (item as? CurrentShopTitleModel)?.let {
            binding.item = item
        }
    }
}