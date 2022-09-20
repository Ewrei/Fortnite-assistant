package robin.vitalij.fortniteassitant.ui.cosmetics.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity

class CosmeticsAdapter(
    private val onClick: (cosmeticsEntity: CosmeticsEntity) -> Unit
) : RecyclerView.Adapter<CosmeticsHolder>() {

    private val items = mutableListOf<CosmeticsEntity>()

    fun setData(data: List<CosmeticsEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CosmeticsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cosmetics,
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
