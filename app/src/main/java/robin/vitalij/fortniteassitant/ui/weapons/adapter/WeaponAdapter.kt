package robin.vitalij.fortniteassitant.ui.weapons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemBannerBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponAdapter(
    private val onClick: (weaponEntity: WeaponEntity) -> Unit
) : RecyclerView.Adapter<WeaponHolder>() {

    private val items = mutableListOf<WeaponEntity>()

    fun updateData(data: List<WeaponEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeaponHolder(
        ItemWeaponBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeaponHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
