package robin.vitalij.fortniteassitant.ui.banners.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannersAdapter(
    private val onClick: (bannerEntity: BannerEntity) -> Unit
) : RecyclerView.Adapter<BannersHolder>() {

    private val items = mutableListOf<BannerEntity>()

    fun setData(data: List<BannerEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_banner,
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
