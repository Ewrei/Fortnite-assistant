package robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.FishEntity

class FishResultAdapter : RecyclerView.Adapter<FishResultHolder>() {

    private val items = mutableListOf<FishEntity>()

    fun setData(data: List<FishEntity>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FishResultHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fish_result,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FishResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
