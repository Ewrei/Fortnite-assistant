package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class ContactUsResultViewModel(private val resourceProvider: ResourceProvider) : BaseViewModel() {

    var mutableLiveData = MutableLiveData<List<ContactUsModel>>()


    fun loadData(isContactUs: Boolean) {
        if(isContactUs) {
            mutableLiveData.value = getContacts()
        } else {
            mutableLiveData.value = getFoundAccountId()
        }
    }

    private fun getContacts(): List<ContactUsModel> {
        val list = mutableListOf<ContactUsModel>()
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

    private fun getFoundAccountId(): List<ContactUsModel> {
        val list = mutableListOf<ContactUsModel>()
        list.add(ContactUsModel(resourceProvider.getString(R.string.found_account_id_in_epic_games_url), ConfigType.FOUND_ACCOUNT_ID_IN_EPIC_GAMES))
        list.add(ContactUsModel(resourceProvider.getString(R.string.found_account_id_in_fortnite_url), ConfigType.FOUND_ACCOUNT_ID_IN_FORTNITE))

        return list
    }

}