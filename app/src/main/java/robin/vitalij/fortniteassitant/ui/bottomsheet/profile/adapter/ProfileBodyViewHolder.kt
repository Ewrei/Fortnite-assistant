package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.BodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats

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

    private fun initAdapter(list: List<BodyStats>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = BodyStatsAdapter()
            (adapter as BodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}