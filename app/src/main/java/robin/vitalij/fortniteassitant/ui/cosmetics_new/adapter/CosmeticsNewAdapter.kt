package robin.vitalij.fortniteassitant.ui.cosmetics_new.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsNewBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

class CosmeticsNewAdapter(
    private val onClick: (cosmeticsNewEntity: CosmeticsNewEntity) -> Unit
) : RecyclerView.Adapter<CosmeticsNewHolder>() {

    private val items = mutableListOf<CosmeticsNewEntity>()

    fun updateData(data: List<CosmeticsNewEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CosmeticsNewHolder(
        ItemCosmeticsNewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CosmeticsNewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
