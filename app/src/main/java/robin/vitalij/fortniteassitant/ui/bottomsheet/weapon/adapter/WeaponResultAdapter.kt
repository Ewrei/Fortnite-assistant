package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemWeaponResultBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponResultAdapter : RecyclerView.Adapter<WeaponResultHolder>() {

    private val items = mutableListOf<WeaponEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<WeaponEntity>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeaponResultHolder(
        ItemWeaponResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeaponResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
