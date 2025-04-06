package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ComparisonStatisticsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ComparisonStatisticsListItem>()

    private val delegates = listOf(
        ComparisonStatisticsScheduleDelegate(),
        ComparisonStatisticsHeaderDelegate(),
        ComparisonStatisticsDelegate()
    )

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ComparisonStatisticsListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return delegates.indexOfFirst { it.isForViewType(item) }
            .takeIf { it != -1 } ?: error("No delegate found for item at position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        delegates[getItemViewType(position)].onBindViewHolder(holder, item)
    }

    override fun getItemCount(): Int = items.size

}