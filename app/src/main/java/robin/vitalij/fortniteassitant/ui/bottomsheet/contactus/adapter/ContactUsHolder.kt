package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ButtonBinding.setConfig
import robin.vitalij.fortniteassitant.databinding.ItemContactUsBinding
import robin.vitalij.fortniteassitant.model.ContactUsModel

class ContactUsHolder(private val binding: ItemContactUsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ContactUsModel) {
        binding.button.setConfig(item.configType)
    }
}
