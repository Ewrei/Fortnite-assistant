package robin.vitalij.fortniteassitant.ui.banners.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBannerBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannersAdapter(
    private val onClick: (bannerEntity: BannerEntity) -> Unit
) : RecyclerView.Adapter<BannersHolder>() {

    private val items = mutableListOf<BannerEntity>()

    fun updateData(data: List<BannerEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannersHolder(
        ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BannersHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
