package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

class WeaponResultAdapter(
) : RecyclerView.Adapter<WeaponResultHolder>() {

    private val items = mutableListOf<WeaponEntity>()

    fun setData(data: List<WeaponEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeaponResultHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_weapon_result,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeaponResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
