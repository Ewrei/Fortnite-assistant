package robin.vitalij.fortniteassitant.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.HomeHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.HomeStatisticsViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeType

class HomeAdapter(
) : RecyclerView.Adapter<BaseViewHolder<Home>>() {

    private val items = arrayListOf<Home>()

    fun setData(data: List<Home>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<Home>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Home> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HomeType.HEADER.id -> {
                return HomeHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_header,
                        parent,
                        false
                    )
                )
            }
            HomeType.STATISTICS.id -> {
                return HomeStatisticsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_statistics,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return HomeHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_header,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<Home>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
