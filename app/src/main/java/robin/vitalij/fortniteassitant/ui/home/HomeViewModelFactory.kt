package robin.vitalij.fortniteassitant.ui.home

import robin.vitalij.fortniteassitant.repository.db.HomeRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private var homeRepository: HomeRepository,
    private var preferenceManager: PreferenceManager
) : BaseViewModelFactory<HomeViewModel>(
    HomeViewModel::class.java) {

    private var viewModel: HomeViewModel? = null

    override fun createViewModel(): HomeViewModel {
        return viewModel ?: run {
            val model =
                HomeViewModel(
                    homeRepository,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}