package robin.vitalij.fortniteassitant.ui.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.top.adapter.viewholder.TopHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.top.adapter.viewholder.TopViewHolder
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.Top
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.TopViewModelType

class TopAdapter(
    private val onClick: (accountId: String) -> Unit,
    private val onTopClick: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<Top>>() {

    private val items = arrayListOf<Top>()

    fun setData(data: List<Top>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<Top>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Top> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TopViewModelType.HEADER.id -> {
                return TopHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_header,
                        parent,
                        false
                    ),
                    onTopClick
                )
            }
            TopViewModelType.TOP.id -> {
                return TopViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top,
                        parent,
                        false
                    ), onClick
                )
            }
            else -> {
                return TopViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top,
                        parent,
                        false
                    ), onClick
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Top>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getType().id
}
