package robin.vitalij.fortniteassitant.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.history.adapter.viewholder.HistoryMatchDateViewHolder
import robin.vitalij.fortniteassitant.ui.history.adapter.viewholder.HistoryMatchViewHolder
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatchType

class HistoryMatchAdapter(
) : RecyclerView.Adapter<BaseViewHolder<HistoryMatch>>() {

    private val items = arrayListOf<HistoryMatch>()

    fun setData(data: List<HistoryMatch>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<HistoryMatch>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HistoryMatch> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HistoryMatchType.DATE.id -> {
                return HistoryMatchDateViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_history_match_date,
                        parent,
                        false
                    )
                )
            }
            HistoryMatchType.MATCHES.id -> {
                return HistoryMatchViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_history_match,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return HistoryMatchDateViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_history_match_date,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<HistoryMatch>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
