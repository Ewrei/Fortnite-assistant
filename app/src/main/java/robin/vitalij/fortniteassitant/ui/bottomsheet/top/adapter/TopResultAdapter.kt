package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemTopResultContentBinding
import robin.vitalij.fortniteassitant.databinding.ItemTopResultHeaderBinding
import robin.vitalij.fortniteassitant.model.enums.TopType

class TopResultAdapter(private val onClick: (topType: TopType) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        when (viewType) {
            R.layout.item_top_result_content -> {
                return TopContentViewHolder(
                    ItemTopResultContentBinding.inflate(
                        inflater,
                        parent,
                        false
                    ), onClick
                )
            }

            R.layout.item_top_result_header -> {
                return TopHeaderViewHolder(
                    ItemTopResultHeaderBinding.inflate(
                        inflater,
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
            is TopListItem.ContentItem -> (holder as TopContentViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is TopListItem.HeaderItem -> R.layout.item_top_result_header
        is TopListItem.ContentItem -> R.layout.item_top_result_content
    }

}