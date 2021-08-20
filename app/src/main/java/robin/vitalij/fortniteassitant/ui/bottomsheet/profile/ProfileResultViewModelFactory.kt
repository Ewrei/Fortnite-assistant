package robin.vitalij.fortniteassitant.ui.bottomsheet.profile

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class ProfileResultViewModelFactory @Inject constructor(
    private val getUserRepository: GetUserRepository,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val comparisonRepository: ComparisonRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<ProfileResultViewModel>(ProfileResultViewModel::class.java) {

    private var viewModel: ProfileResultViewModel? = null

    override fun createViewModel(): ProfileResultViewModel {
        return viewModel ?: run {
            val model = ProfileResultViewModel(
                getUserRepository,
                comparisonListUserRepository,
                comparisonRepository,
                resourceProvider
            )
            viewModel = model
            return model
        }
    }
}