package robin.vitalij.fortniteassitant.ui.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R

class TopAdapter(
    private val onClick: (accountId: String) -> Unit
) : RecyclerView.Adapter<TopHolder>() {

    private val items = arrayListOf<TopUserModel>()

    fun setData(data: List<TopUserModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TopHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_top,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TopHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position].playerId)
        }
    }

    override fun getItemCount() = items.size
}
