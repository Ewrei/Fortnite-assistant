package robin.vitalij.fortniteassitant.ui.charts

import robin.vitalij.fortniteassitant.repository.db.ChartsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ChartsViewModelFactory @Inject constructor(
    private var chartsRepository: ChartsRepository,
    private var preferenceManager: PreferenceManager
) : BaseViewModelFactory<ChartsViewModel>(ChartsViewModel::class.java) {

    private var viewModel: ChartsViewModel? = null

    override fun createViewModel(): ChartsViewModel {
        return viewModel ?: run {
            val model = ChartsViewModel(
                chartsRepository,
                preferenceManager
            )
            viewModel = model
            return model
        }
    }
}