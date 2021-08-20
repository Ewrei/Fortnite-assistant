package robin.vitalij.fortniteassitant.ui.comparison.viewpager

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterComparisonViewModelFactory @Inject constructor(
    private val comparisonRepository: ComparisonRepository
) : BaseViewModelFactory<AdapterComparisonViewModel>(AdapterComparisonViewModel::class.java) {

    private var viewModel: AdapterComparisonViewModel? = null

    override fun createViewModel(): AdapterComparisonViewModel {
        return viewModel ?: run {
            val model =
                AdapterComparisonViewModel(comparisonRepository)
            viewModel = model
            return model
        }
    }
}