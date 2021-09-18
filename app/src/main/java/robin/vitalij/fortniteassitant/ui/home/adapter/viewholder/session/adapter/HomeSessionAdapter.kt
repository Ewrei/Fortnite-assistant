package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel

class HomeSessionAdapter(
    private val openSessions: () -> Unit,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<HomeSessionListItem>()

    fun setData(data: List<HomeSessionListItem>) {
        items.clear()
        items.addAll(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<HomeSessionListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_home_session_other -> {
                HomeOtherViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_session_other,
                        parent,
                        false
                    ),
                    openSessions
                )
            }
            R.layout.item_home_session_card -> {
                HomeSessionViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_session_card,
                        parent,
                        false
                    ),
                    openSession
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeSessionListItem.OtherItem -> (holder as HomeOtherViewHolder).bind(
                item
            )
            is HomeSessionListItem.SessionItem -> (holder as HomeSessionViewHolder).bind(
                item
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is HomeSessionListItem.OtherItem -> R.layout.item_home_session_other
        is HomeSessionListItem.SessionItem -> R.layout.item_home_session_card
    }
}