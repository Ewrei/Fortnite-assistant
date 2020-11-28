package robin.vitalij.fortniteassitant.ui.historymatch

import robin.vitalij.fortniteassitant.repository.db.HistoryMatchRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class HistoryMatchViewModelFactory @Inject constructor(
    private val historyRepository: HistoryMatchRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<HistoryMatchViewModel>(
    HistoryMatchViewModel::class.java
) {

    private var viewModel: HistoryMatchViewModel? = null

    override fun createViewModel(): HistoryMatchViewModel {
        return viewModel ?: run {
            val model = HistoryMatchViewModel(historyRepository, preferenceManager)
            viewModel = model
            return model
        }
    }
}