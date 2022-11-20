package robin.vitalij.fortniteassitant.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemHistoryBinding
import robin.vitalij.fortniteassitant.databinding.ItemHomeHeaderBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class HistoryAdapter(private val onClick: (sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel>) -> Unit) :
    RecyclerView.Adapter<HistoryHolder>() {

    private val items = mutableListOf<HistoryUserModel>()

    fun updateData(data: List<HistoryUserModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryHolder(
        ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onClick
    )

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
