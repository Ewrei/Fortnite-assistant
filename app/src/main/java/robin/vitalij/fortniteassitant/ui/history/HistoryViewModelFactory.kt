package robin.vitalij.fortniteassitant.ui.history

import robin.vitalij.fortniteassitant.repository.db.HistoryRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class HistoryViewModelFactory @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<HistoryViewModel>(HistoryViewModel::class.java) {

    private var viewModel: HistoryViewModel? = null

    override fun createViewModel(): HistoryViewModel {
        return viewModel ?: run {
            val model =
                HistoryViewModel(
                    historyRepository,
                    preferenceManager,
                    resourceProvider
                )
            viewModel = model
            return model
        }
    }
}