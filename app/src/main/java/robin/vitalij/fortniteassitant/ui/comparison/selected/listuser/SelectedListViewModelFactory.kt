package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class SelectedListViewModelFactory @Inject constructor(
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val resourceProvider: ResourceProvider,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<SelectedListViewModel>(SelectedListViewModel::class.java) {

    private var viewModel: SelectedListViewModel? = null

    override fun createViewModel(): SelectedListViewModel {
        return viewModel ?: run {
            val model =
                SelectedListViewModel(
                    comparisonListUserRepository,
                    resourceProvider,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}