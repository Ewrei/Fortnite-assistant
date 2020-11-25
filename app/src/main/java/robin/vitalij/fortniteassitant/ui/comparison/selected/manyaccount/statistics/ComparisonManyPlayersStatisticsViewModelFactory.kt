package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class ComparisonManyPlayersStatisticsViewModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val comparisonListUserRepository: ComparisonListUserRepository
) : BaseViewModelFactory<ComparisonManyPlayersStatisticsViewModel>(
    ComparisonManyPlayersStatisticsViewModel::class.java
) {

    private var viewModel: ComparisonManyPlayersStatisticsViewModel? = null

    override fun createViewModel(): ComparisonManyPlayersStatisticsViewModel {
        return viewModel ?: run {
            val model =
                ComparisonManyPlayersStatisticsViewModel(
                    resourceProvider,
                    comparisonListUserRepository
                )
            viewModel = model
            return model
        }
    }
}