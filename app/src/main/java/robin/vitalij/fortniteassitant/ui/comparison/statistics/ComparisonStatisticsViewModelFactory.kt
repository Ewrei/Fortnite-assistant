package robin.vitalij.fortniteassitant.ui.comparison.statistics

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonProfileRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class ComparisonStatisticsViewModelFactory @Inject constructor(
    private val comparisonProfileRepository: ComparisonProfileRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<ComparisonStatisticsViewModel>(ComparisonStatisticsViewModel::class.java) {

    private var viewModel: ComparisonStatisticsViewModel? = null

    override fun createViewModel(): ComparisonStatisticsViewModel {
        return viewModel ?: run {
            val model =
                ComparisonStatisticsViewModel(
                    comparisonProfileRepository,
                    resourceProvider
                )
            viewModel = model
            return model
        }
    }
}