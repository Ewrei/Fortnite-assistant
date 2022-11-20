package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.varians_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemOptionBinding
import robin.vitalij.fortniteassitant.db.entity.Option

internal class VariantOptionAdapter(private val widthPixels: Int) :
    RecyclerView.Adapter<VariantOptionHolder>() {

    private val items = mutableListOf<Option>()

    fun setData(data: List<Option>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VariantOptionHolder(
        ItemOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), widthPixels
    )

    override fun onBindViewHolder(holder: VariantOptionHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}