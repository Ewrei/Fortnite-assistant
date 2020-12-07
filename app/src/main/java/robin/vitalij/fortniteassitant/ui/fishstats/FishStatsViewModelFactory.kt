package robin.vitalij.fortniteassitant.ui.fishstats

import robin.vitalij.fortniteassitant.repository.FishStatsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class FishStatsViewModelFactory @Inject constructor(
    private var fishStatsRepository: FishStatsRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<FishStatsViewModel>(FishStatsViewModel::class.java) {

    private var viewModel: FishStatsViewModel? = null

    override fun createViewModel(): FishStatsViewModel {
        return viewModel ?: run {
            val model =
                FishStatsViewModel(fishStatsRepository, preferenceManager, resourceProvider)
            viewModel = model
            return model
        }
    }
}