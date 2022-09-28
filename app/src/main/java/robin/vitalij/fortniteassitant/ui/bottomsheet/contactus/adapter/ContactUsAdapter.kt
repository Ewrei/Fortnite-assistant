package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemContactUsBinding
import robin.vitalij.fortniteassitant.model.ContactUsModel

class ContactUsAdapter(val onClick: (contactUsModel: ContactUsModel) -> Unit) :
    RecyclerView.Adapter<ContactUsHolder>() {

    private val items = mutableListOf<ContactUsModel>()

    fun updateData(data: List<ContactUsModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactUsHolder(
        ItemContactUsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ContactUsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}