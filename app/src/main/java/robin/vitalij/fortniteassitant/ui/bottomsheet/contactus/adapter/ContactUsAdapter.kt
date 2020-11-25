package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ContactUsModel

internal class ContactUsAdapter(val onclick: (contactUsModel: ContactUsModel) -> Unit) :
    RecyclerView.Adapter<ContactUsHolder>() {

    private val items = arrayListOf<ContactUsModel>()

    fun setData(data: List<ContactUsModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactUsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_contact_us,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ContactUsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onclick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
