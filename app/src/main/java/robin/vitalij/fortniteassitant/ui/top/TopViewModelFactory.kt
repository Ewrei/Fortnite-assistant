package robin.vitalij.fortniteassitant.ui.top

import robin.vitalij.fortniteassitant.repository.network.TopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class TopViewModelFactory @Inject constructor(
    private val topRepository: TopRepository
) : BaseViewModelFactory<TopViewModel>(TopViewModel::class.java) {

    private var viewModel: TopViewModel? = null

    override fun createViewModel(): TopViewModel {
        return viewModel ?: run {
            val model =
                TopViewModel(topRepository)
            viewModel = model
            return model
        }
    }
}