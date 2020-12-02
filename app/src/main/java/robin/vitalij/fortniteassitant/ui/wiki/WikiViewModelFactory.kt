package robin.vitalij.fortniteassitant.ui.wiki

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class WikiViewModelFactory @Inject constructor() : BaseViewModelFactory<WikiViewModel>(WikiViewModel::class.java) {

    private var viewModel: WikiViewModel? = null

    override fun createViewModel(): WikiViewModel {
        return viewModel ?: run {
            val model =
                WikiViewModel()
            viewModel = model
            return model
        }
    }
}