package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemComparisonManyPlayersHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemComparisonManyPlayersScheduleBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewholder.ComparisonManyPlayersHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewholder.ComparisonManyPlayersScheduleViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayersType

class ComparisonManyPlayerAdapter(
    val onClick: (profileAdapterImpl: ComparisonManyPlayers) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<ComparisonManyPlayers>>() {

    private val items = mutableListOf<ComparisonManyPlayers>()

    fun setData(data: List<ComparisonManyPlayers>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ComparisonManyPlayers> {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ComparisonManyPlayersType.SCHEDULE.id -> {
                val binding = DataBindingUtil.inflate<ItemComparisonManyPlayersScheduleBinding>(
                    inflater,
                    R.layout.item_comparison_many_players_schedule,
                    parent,
                    false
                )
                return ComparisonManyPlayersScheduleViewHolder(binding)
            }
            ComparisonManyPlayersType.HEADER.id -> {
                val binding =
                    DataBindingUtil.inflate<ItemComparisonManyPlayersHeaderBinding>(
                        inflater,
                        R.layout.item_comparison_many_players_header,
                        parent,
                        false
                    )
                return ComparisonManyPlayersHeaderViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemComparisonManyPlayersScheduleBinding>(
                    inflater,
                    R.layout.item_comparison_many_players_schedule,
                    parent,
                    false
                )
                return ComparisonManyPlayersScheduleViewHolder(binding)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<ComparisonManyPlayers>, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
