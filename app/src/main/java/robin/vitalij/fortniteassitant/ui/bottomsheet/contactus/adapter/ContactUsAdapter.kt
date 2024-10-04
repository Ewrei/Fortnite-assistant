package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemContactUsBinding
import robin.vitalij.fortniteassitant.model.ContactUsModel

class ContactUsAdapter(val onClick: (contactUsModel: ContactUsModel) -> Unit) :
    ListAdapter<ContactUsModel, ContactUsHolder>(ContactUsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactUsHolder(
        ItemContactUsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ContactUsHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }

    object ContactUsComparator : DiffUtil.ItemCallback<ContactUsModel>() {

        override fun areItemsTheSame(oldItem: ContactUsModel, newItem: ContactUsModel) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ContactUsModel, newItem: ContactUsModel) =
            oldItem == newItem

    }

}