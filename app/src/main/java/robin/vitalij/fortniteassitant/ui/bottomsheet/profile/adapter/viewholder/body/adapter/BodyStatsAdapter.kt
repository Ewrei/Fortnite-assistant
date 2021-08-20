package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder.BodyStatsEmptyViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder.BodyStatsLtmViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder.BodyStatsOverallViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder.BodyStatsStatsViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStatsType
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class BodyStatsAdapter() : RecyclerView.Adapter<BaseViewHolder<BodyStats>>() {

    private val items = arrayListOf<BodyStats>()

    fun setData(data: List<BodyStats>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<BodyStats>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BodyStats> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            BodyStatsType.EMPTY.id -> {
                return BodyStatsEmptyViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_empty,
                        parent,
                        false
                    )
                )
            }
            BodyStatsType.OVERALL.id -> {
                return BodyStatsOverallViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_overall,
                        parent,
                        false
                    )
                )
            }
            BodyStatsType.LTM.id -> {
                return BodyStatsLtmViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_ltm,
                        parent,
                        false
                    )
                )
            }
            BodyStatsType.STATS.id -> {
                return BodyStatsStatsViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_stats,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return BodyStatsEmptyViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_body_stats_empty,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<BodyStats>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
