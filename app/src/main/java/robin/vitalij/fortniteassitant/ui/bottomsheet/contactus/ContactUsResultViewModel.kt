package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ContactUsResultViewModel(private val resourceProvider: ResourceProvider) : BaseViewModel() {

    var mutableLiveData = MutableLiveData<List<ContactUsModel>>()

    init {
        mutableLiveData.value = getContacts()
    }

    private fun getContacts(): List<ContactUsModel> {
        val list = arrayListOf<ContactUsModel>()
        list.add(ContactUsModel(resourceProvider.getString(R.string.gmail), ConfigType.GMAIL))
        list.add(
            ContactUsModel(
                resourceProvider.getString(R.string.telegram_url),
                ConfigType.TELEGRAM
            )
        )
        list.add(ContactUsModel(resourceProvider.getString(R.string.vk_url), ConfigType.VK))
        return list
    }
}