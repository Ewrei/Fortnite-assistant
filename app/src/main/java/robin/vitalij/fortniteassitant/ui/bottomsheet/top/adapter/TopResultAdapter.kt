package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewholder.TopContentViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewholder.TopHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResultType
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class TopResultAdapter(private val onClick: (topType: TopType) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<TopResult>>() {

    private val items = mutableListOf<TopResult>()

    fun setData(data: List<TopResult>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TopResult> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TopResultType.CONTENT.id -> {
                return TopContentViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_result_content,
                        parent,
                        false
                    ), onClick
                )
            }
            TopResultType.HEADER.id -> {
                return TopHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_result_header,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return TopHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_top_result_header,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<TopResult>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id

}