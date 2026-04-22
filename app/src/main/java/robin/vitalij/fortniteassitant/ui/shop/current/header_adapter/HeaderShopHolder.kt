package robin.vitalij.fortniteassitant.ui.shop.current.header_adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemHeaderShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopEntry
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.CurrentShopAdapter
import robin.vitalij.fortniteassitant.utils.GridSpacingItemDecoration

class HeaderShopHolder(
    private val binding: ItemHeaderShopBinding,
    private val onClick: (shopNewItem: ShopEntry) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShopAdapterItem) {
        binding.name.text = item.section.name
        binding.size.setValueText(item.shops.size)

        binding.recyclerView.run {
            if (adapter == null) {
                adapter = initAdapter(item.shops)

                layoutManager = GridLayoutManager(binding.root.context, MAX_SPAN_COUNT)
                addItemDecoration(GridSpacingItemDecoration(MAX_SPAN_COUNT, 20, true))
            } else {
                (adapter as CurrentShopAdapter).updateData(item.shops)
            }
        }
    }

    private fun initAdapter(list: List<ShopEntry>) =
        CurrentShopAdapter(onClick).apply {
            setData(list)
        }

    companion object {
        private const val MAX_SPAN_COUNT = 2
    }
}