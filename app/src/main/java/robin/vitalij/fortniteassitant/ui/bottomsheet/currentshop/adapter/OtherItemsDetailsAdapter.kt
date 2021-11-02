package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel
import robin.vitalij.fortniteassitant.model.network.shop.OtherItemsDetails

internal class OtherItemsDetailsAdapter(
    private val widthPixels: Int
) : RecyclerView.Adapter<OtherItemsDetailsHolder>() {

    private val items = arrayListOf<GrantedModel>()

    fun setData(data: List<GrantedModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OtherItemsDetailsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_other_items_details,
            parent,
            false
        ), widthPixels
    )

    override fun onBindViewHolder(holder: OtherItemsDetailsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
