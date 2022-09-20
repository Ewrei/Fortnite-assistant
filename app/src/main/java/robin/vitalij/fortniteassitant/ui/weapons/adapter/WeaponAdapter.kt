package robin.vitalij.fortniteassitant.ui.weapons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponAdapter(
    private val onClick: (weaponEntity: WeaponEntity) -> Unit
) : RecyclerView.Adapter<WeaponHolder>() {

    private val items = mutableListOf<WeaponEntity>()

    fun setData(data: List<WeaponEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeaponHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_weapon,
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
