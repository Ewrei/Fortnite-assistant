package robin.vitalij.fortniteassitant.ui.bottomsheet.top

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class TopResultViewModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<TopResultViewModel>(TopResultViewModel::class.java) {

    private var viewModel: TopResultViewModel? = null

    override fun createViewModel(): TopResultViewModel {
        return viewModel ?: run {
            val model = TopResultViewModel(resourceProvider)
            viewModel = model
            return model
        }
    }
}