package robin.vitalij.fortniteassitant.ui.session.statistics

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.mapper.HomeSessionRepository
import javax.inject.Inject

class DetailsSessionStatisticsViewModelFactory @Inject constructor(
    private val homeSessionRepository: HomeSessionRepository
) : BaseViewModelFactory<DetailsSessionStatisticsViewModel>(
    DetailsSessionStatisticsViewModel::class.java
) {

    private var viewModel: DetailsSessionStatisticsViewModel? = null

    override fun createViewModel(): DetailsSessionStatisticsViewModel {
        return viewModel ?: run {
            val model =
                DetailsSessionStatisticsViewModel(homeSessionRepository)
            viewModel = model
            return model
        }
    }
}