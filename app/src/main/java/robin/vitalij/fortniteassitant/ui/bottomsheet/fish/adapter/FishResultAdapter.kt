package robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishResultBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity

class FishResultAdapter : RecyclerView.Adapter<FishResultHolder>() {

    private val items = mutableListOf<FishEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<FishEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FishResultHolder(
        ItemFishResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FishResultHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
