package robin.vitalij.fortniteassitant.ui.comparison.statistics

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonProfileRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ComparisonStatisticsViewModelFactory @Inject constructor(
    private val comparisonProfileRepository: ComparisonProfileRepository
) : BaseViewModelFactory<ComparisonStatisticsViewModel>(ComparisonStatisticsViewModel::class.java) {

    private var viewModel: ComparisonStatisticsViewModel? = null

    override fun createViewModel(): ComparisonStatisticsViewModel {
        return viewModel ?: run {
            val model =
                ComparisonStatisticsViewModel(comparisonProfileRepository)
            viewModel = model
            return model
        }
    }
}