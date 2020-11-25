package robin.vitalij.fortniteassitant.ui.comparison.selected

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ComparisonSelectedViewModelFactory @Inject constructor(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val saveUserRepository: SaveUserRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModelFactory<ComparisonSelectedViewModel>(
    ComparisonSelectedViewModel::class.java
) {

    private var viewModel: ComparisonSelectedViewModel? = null

    override fun createViewModel(): ComparisonSelectedViewModel {
        return viewModel ?: run {
            val model =
                ComparisonSelectedViewModel(
                    getSearchUserRepository,
                    comparisonListUserRepository,
                    saveUserRepository,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}