package robin.vitalij.fortniteassitant.ui.users

import robin.vitalij.fortniteassitant.repository.db.UsersRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class UsersViewModelFactory @Inject constructor(
    private var usersRepository: UsersRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val resourceProvider: ResourceProvider,
    private var preferenceManager: PreferenceManager
) : BaseViewModelFactory<UsersViewModel>(UsersViewModel::class.java) {

    private var viewModel: UsersViewModel? = null

    override fun createViewModel(): UsersViewModel {
        return viewModel ?: run {
            val model =
                UsersViewModel(
                    usersRepository,
                    saveUserRepository,
                    getUserRepository,
                    resourceProvider,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}