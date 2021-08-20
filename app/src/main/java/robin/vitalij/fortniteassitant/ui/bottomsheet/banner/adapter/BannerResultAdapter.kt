package robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

class BannerResultAdapter(
) : RecyclerView.Adapter<BannerResultHolder>() {

    private val items = arrayListOf<BannerEntity>()

    fun setData(data: List<BannerEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannerResultHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_banner_result,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BannerResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
