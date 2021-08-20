package robin.vitalij.fortniteassitant.ui.cosmeticsnew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

class CosmeticsNewAdapter(
    private val onClick: (cosmeticsNewEntity: CosmeticsNewEntity) -> Unit
) : RecyclerView.Adapter<CosmeticsNewHolder>() {

    private val items = arrayListOf<CosmeticsNewEntity>()

    fun setData(data: List<CosmeticsNewEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CosmeticsNewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cosmetics_new,
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
