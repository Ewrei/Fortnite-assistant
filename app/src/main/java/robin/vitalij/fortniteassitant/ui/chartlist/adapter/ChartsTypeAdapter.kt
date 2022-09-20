package robin.vitalij.fortniteassitant.ui.chartlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class ChartsTypeAdapter(private val onClick: (chartsType: ChartsType) -> Unit) :
    RecyclerView.Adapter<ChartsTypeHolder>() {

    private val items = mutableListOf<ChartsType>()

    fun setData(data: List<ChartsType>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChartsTypeHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_charts_type,
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
