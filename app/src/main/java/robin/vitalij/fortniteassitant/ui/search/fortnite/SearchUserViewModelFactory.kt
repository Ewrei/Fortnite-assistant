package robin.vitalij.fortniteassitant.ui.search.fortnite

import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SearchUserViewModelFactory @Inject constructor(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<SearchUserViewModel>(SearchUserViewModel::class.java) {

    private var viewModel: SearchUserViewModel? = null

    override fun createViewModel(): SearchUserViewModel {
        return viewModel ?: run {
            val model =
                SearchUserViewModel(
                    getSearchUserRepository,
                    saveUserRepository,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}