package robin.vitalij.fortniteassitant.ui.cosmetics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.model.enums.ShopType

class CatalogCosmeticsAdapter(
    private val onClick: (shopType: ShopType) -> Unit
) : RecyclerView.Adapter<CatalogCosmeticsHolder>() {

    private val items = arrayListOf<ShopType>()

    fun setData(data: List<ShopType>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CatalogCosmeticsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_catalog_cosmetics,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CatalogCosmeticsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
