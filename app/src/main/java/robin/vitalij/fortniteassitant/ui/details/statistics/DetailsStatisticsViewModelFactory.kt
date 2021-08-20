package robin.vitalij.fortniteassitant.ui.details.statistics

import robin.vitalij.fortniteassitant.repository.db.DetailsStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class DetailsStatisticsViewModelFactory @Inject constructor(
    private val detailsStatisticsRepository: DetailsStatisticsRepository,
    private var preferenceManager: PreferenceManager
) : BaseViewModelFactory<DetailsStatisticsViewModel>(
    DetailsStatisticsViewModel::class.java
) {

    private var viewModel: DetailsStatisticsViewModel? = null

    override fun createViewModel(): DetailsStatisticsViewModel {
        return viewModel ?: run {
            val model =
                DetailsStatisticsViewModel(
                    detailsStatisticsRepository,
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}