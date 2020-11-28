package robin.vitalij.fortniteassitant.ui.season.statistics

import robin.vitalij.fortniteassitant.repository.network.GetSeasonStatisticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class DetailsSeasonStatisticsViewModelFactory @Inject constructor(
    private val getSeasonStatisticsRepository: GetSeasonStatisticsRepository
) : BaseViewModelFactory<DetailsSeasonStatisticsViewModel>(
    DetailsSeasonStatisticsViewModel::class.java
) {

    private var viewModel: DetailsSeasonStatisticsViewModel? = null

    override fun createViewModel(): DetailsSeasonStatisticsViewModel {
        return viewModel ?: run {
            val model =
                DetailsSeasonStatisticsViewModel(
                    getSeasonStatisticsRepository
                )
            viewModel = model
            return model
        }
    }
}