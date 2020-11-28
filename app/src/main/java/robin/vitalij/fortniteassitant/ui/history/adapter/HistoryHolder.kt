package robin.vitalij.fortniteassitant.ui.history.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_SHORT_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemHistoryBinding
import robin.vitalij.fortniteassitant.model.HistoryUserModel

class HistoryHolder(
    var binding: ItemHistoryBinding,
    val onClick: (sessionId: Long, sessionLast: Long, sessionDate: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryUserModel) {
        binding.item = item

        itemView.cardView.setOnClickListener {
            onClick(
                item.sessionId, item.lastSessionId,
                "${item.startTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)} - ${
                    item.endTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                }"
            )
        }

        itemView.arcProgress.setAnimatedProgress(item.userEntity.all?.overall?.winRate ?: 0.0)
        itemView.arcProgress.setBottomText(itemView.context.getString(R.string.win_rate))
    }
}