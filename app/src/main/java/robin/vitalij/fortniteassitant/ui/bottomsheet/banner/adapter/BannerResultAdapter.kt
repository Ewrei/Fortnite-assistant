package robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBannerResultBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannerResultAdapter : RecyclerView.Adapter<BannerResultHolder>() {

    private val items = mutableListOf<BannerEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<BannerEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannerResultHolder(
        ItemBannerResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BannerResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
