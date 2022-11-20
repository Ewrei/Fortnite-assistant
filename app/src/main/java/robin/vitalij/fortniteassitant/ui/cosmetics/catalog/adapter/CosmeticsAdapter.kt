package robin.vitalij.fortniteassitant.ui.cosmetics.catalog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemCatalogCosmeticsBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class CosmeticsAdapter(
    private val onClick: (cosmeticsEntity: CosmeticsEntity) -> Unit
) : RecyclerView.Adapter<CosmeticsHolder>() {

    private val items = mutableListOf<CosmeticsEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<CosmeticsEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CosmeticsHolder(
        ItemCosmeticsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CosmeticsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
