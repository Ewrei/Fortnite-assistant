package robin.vitalij.fortniteassitant.ui.history

import robin.vitalij.fortniteassitant.repository.db.HistoryRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class HistoryViewModelFactory @Inject constructor(
    private var historyRepository: HistoryRepository,
    private var preferenceManager: PreferenceManager
) : BaseViewModelFactory<HistoryViewModel>(HistoryViewModel::class.java) {

    private var viewModel: HistoryViewModel? = null

    override fun createViewModel(): HistoryViewModel {
        return viewModel ?: run {
            val model =
                HistoryViewModel(historyRepository, preferenceManager)
            viewModel = model
            return model
        }
    }
}