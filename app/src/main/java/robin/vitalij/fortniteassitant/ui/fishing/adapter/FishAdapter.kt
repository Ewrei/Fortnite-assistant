package robin.vitalij.fortniteassitant.ui.fishing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class FishAdapter(
    private val onClick: (fishEntity: FishEntity) -> Unit
) : RecyclerView.Adapter<FishHolder>() {

    private val items = mutableListOf<FishEntity>()

    fun updateData(data: List<FishEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FishHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fish,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FishHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
