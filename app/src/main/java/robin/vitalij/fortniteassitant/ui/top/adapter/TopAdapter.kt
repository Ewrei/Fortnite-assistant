package robin.vitalij.fortniteassitant.ui.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.NewsModel

class TopAdapter(
    private val onClick: (accountId: String) -> Unit,
    private val onTopClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<TopListItem>()

    fun updateData(data: List<TopListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_top_header -> {
                TopHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_header,
                        parent,
                        false
                    ),
                    onTopClick
                )
            }
            R.layout.item_top -> {
                TopViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top,
                        parent,
                        false
                    ), onClick
                )
            }
            R.layout.item_top_current -> {
                TopCurrentViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_current,
                        parent,
                        false
                    )
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TopListItem.HeaderItem -> (holder as TopHeaderViewHolder).bind(item)
            is TopListItem.PlayerItem -> (holder as TopViewHolder).bind(item)
            is TopListItem.CurrentPositionItem -> (holder as TopCurrentViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is TopListItem.HeaderItem -> R.layout.item_top_header
        is TopListItem.PlayerItem -> R.layout.item_top
        is TopListItem.CurrentPositionItem -> R.layout.item_top_current
    }
}
