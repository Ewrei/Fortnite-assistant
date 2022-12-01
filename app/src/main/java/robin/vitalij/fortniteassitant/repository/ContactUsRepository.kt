package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactUsRepository @Inject constructor(private val resourceProvider: ResourceProvider) {

    fun getData(isContactUs: Boolean): Flow<LoadingState<List<ContactUsModel>>> = flow {
        emit(LoadingState.Loading)
        emit(LoadingState.Success(if (isContactUs) getContacts() else getFoundAccountId()))
    }.flowOn(Dispatchers.IO)

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
        list.add(
            ContactUsModel(
                resourceProvider.getString(R.string.found_account_id_in_epic_games_url),
                ConfigType.FOUND_ACCOUNT_ID_IN_EPIC_GAMES
            )
        )
        list.add(
            ContactUsModel(
                resourceProvider.getString(R.string.found_account_id_in_fortnite_url),
                ConfigType.FOUND_ACCOUNT_ID_IN_FORTNITE
            )
        )

        return list
    }
}