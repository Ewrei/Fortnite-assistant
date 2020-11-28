package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewholder

import kotlinx.android.synthetic.main.item_home_session_card.view.*
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_SHORT_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionCardBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSessionSessionViewModel

class HomeSessionViewHolder(
    override val binding: ItemHomeSessionCardBinding,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String) -> Unit
) : BaseViewHolder<HomeSession>(binding) {

    override fun bind(item: HomeSession) {
        if (item is HomeSessionSessionViewModel) {
            binding.item = item.historyUserModel
            itemView.cardView.setOnClickListener {
                openSession(
                    item.historyUserModel.sessionId,
                    item.historyUserModel.lastSessionId,
                    "${
                    item.historyUserModel.startTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                    } - ${
                    item.historyUserModel.endTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                    }"
                )
            }
        }
    }
}