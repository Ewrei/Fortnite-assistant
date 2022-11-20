package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter.BodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter.BodyStatsListItem

class ProfileBodyViewHolder(
    private val binding: ItemProfileBodyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileListItem.BodyItem) {
        item.all.let(::initAdapter)

        binding.layoutTypeStatsInclude.allStats.setOnClickListener {
            item.all.let(::initAdapter)
        }

        binding.layoutTypeStatsInclude.keyboardMouse.setOnClickListener {
            item.keyboardMouse.let(::initAdapter)
        }

        binding.layoutTypeStatsInclude.gamepad.setOnClickListener {
            item.gamepad.let(::initAdapter)
        }

        binding.layoutTypeStatsInclude.touch.setOnClickListener {
            item.touch.let(::initAdapter)
        }
    }

    private fun initAdapter(list: List<BodyStatsListItem>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = BodyStatsAdapter()
            (adapter as BodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}