package robin.vitalij.fortniteassitant.ui.chartlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemChartsTypeBinding
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class ChartsTypeAdapter(private val onClick: (chartsType: ChartsType) -> Unit) :
    RecyclerView.Adapter<ChartsTypeHolder>() {

    private val items = mutableListOf<ChartsType>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ChartsType>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChartsTypeHolder(
        ItemChartsTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ChartsTypeHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
