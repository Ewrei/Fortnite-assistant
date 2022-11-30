package robin.vitalij.fortniteassitant.ui.charts_type.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemChartsTypeBinding
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class ChartsTypeAdapter(private val onClick: (chartsType: ChartsType) -> Unit) :
    ListAdapter<ChartsType, ChartsTypeHolder>(ChartsTypeComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChartsTypeHolder(
        ItemChartsTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ChartsTypeHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }

    private object ChartsTypeComparator : DiffUtil.ItemCallback<ChartsType>() {

        override fun areItemsTheSame(oldItem: ChartsType, newItem: ChartsType) =
            oldItem.ordinal == newItem.ordinal

        override fun areContentsTheSame(oldItem: ChartsType, newItem: ChartsType) =
            oldItem == newItem

    }

}
