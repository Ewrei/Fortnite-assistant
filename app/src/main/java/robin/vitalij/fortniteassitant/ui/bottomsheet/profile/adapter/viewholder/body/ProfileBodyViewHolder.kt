package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_profile_body.view.*
import kotlinx.android.synthetic.main.recycler_view.view.*
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.BodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.Profile
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.ProfileBodyViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class ProfileBodyViewHolder(
    override val binding: ItemProfileBodyBinding
) : BaseViewHolder<Profile>(binding) {

    override fun bind(item: Profile) {
        if (item is ProfileBodyViewModel) {
            binding.item = item
            item.all.let(::initAdapter)

            itemView.allStats.setOnClickListener {
                item.all.let(::initAdapter)
            }

            itemView.keyboardMouse.setOnClickListener {
                item.keyboardMouse.let(::initAdapter)
            }

            itemView.gamepad.setOnClickListener {
                item.gamepad.let(::initAdapter)
            }

            itemView.touch.setOnClickListener {
                item.touch.let(::initAdapter)
            }
        }
    }

    private fun initAdapter(list: List<BodyStats>) {
        itemView.recyclerView.run {
            adapter = BodyStatsAdapter()
            (adapter as BodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}