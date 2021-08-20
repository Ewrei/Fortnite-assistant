package robin.vitalij.fortniteassitant.ui.shop.current.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemInventoryContentBinding
import robin.vitalij.fortniteassitant.databinding.ItemInventoryOneColunmBinding
import robin.vitalij.fortniteassitant.databinding.ItemInventoryTitleBinding
import robin.vitalij.fortniteassitant.databinding.ItemInventoryTwoColumnBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.CurrentShopItemHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.CurrentShopOneColumnsHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.CurrentShopTitleHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.CurrentShopTwoColumnsHolder
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopType

class CurrentShopAdapter(
    private val onClick: (shopItem: ShopItem) -> Unit,
    private val widthPixels: Int
) :
    RecyclerView.Adapter<BaseViewHolder<CurrentShopImpl>>() {

    private val items = arrayListOf<CurrentShopImpl>()

    fun setData(data: List<CurrentShopImpl>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CurrentShopImpl> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            CurrentShopType.TWO_COLUMN.id -> {
                val binding = DataBindingUtil.inflate<ItemInventoryTwoColumnBinding>(
                    inflater,
                    R.layout.item_inventory_two_column,
                    parent,
                    false
                )
                return CurrentShopTwoColumnsHolder(binding, onClick, widthPixels)
            }
            CurrentShopType.ONE_COLUMN.id -> {
                val binding = DataBindingUtil.inflate<ItemInventoryOneColunmBinding>(
                    inflater,
                    R.layout.item_inventory_one_colunm,
                    parent,
                    false
                )
                return CurrentShopOneColumnsHolder(binding, onClick, widthPixels)
            }
            CurrentShopType.SHOP_ITEM.id -> {
                val binding = DataBindingUtil.inflate<ItemInventoryContentBinding>(
                    inflater,
                    R.layout.item_inventory_content,
                    parent,
                    false
                )
                return CurrentShopItemHolder(binding, onClick)
            }
            CurrentShopType.TITLE.id -> {
                val binding = DataBindingUtil.inflate<ItemInventoryTitleBinding>(
                    inflater,
                    R.layout.item_inventory_title,
                    parent,
                    false
                )
                return CurrentShopTitleHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemInventoryOneColunmBinding>(
                    inflater,
                    R.layout.item_inventory_one_colunm,
                    parent,
                    false
                )
                return CurrentShopOneColumnsHolder(binding, onClick, widthPixels)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CurrentShopImpl>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getType().id

}
