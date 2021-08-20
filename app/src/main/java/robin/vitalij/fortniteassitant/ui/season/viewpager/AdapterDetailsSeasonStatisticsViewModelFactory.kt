package robin.vitalij.fortniteassitant.ui.season.viewpager

import robin.vitalij.fortniteassitant.repository.network.GetSeasonStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterDetailsSeasonStatisticsViewModelFactory @Inject constructor(
    private val getSeasonStatisticsRepository: GetSeasonStatisticsRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<AdapterDetailsSeasonStatisticsViewModel>(
    AdapterDetailsSeasonStatisticsViewModel::class.java
) {

    private var viewModel: AdapterDetailsSeasonStatisticsViewModel? = null

    override fun createViewModel(): AdapterDetailsSeasonStatisticsViewModel {
        return viewModel ?: run {
            val model =
                AdapterDetailsSeasonStatisticsViewModel(getSeasonStatisticsRepository, preferenceManager)
            viewModel = model
            return model
        }
    }
}