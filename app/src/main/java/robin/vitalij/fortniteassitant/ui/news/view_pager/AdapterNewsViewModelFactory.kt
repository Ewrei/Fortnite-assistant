package robin.vitalij.fortniteassitant.ui.news.view_pager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterNewsViewModelFactory @Inject constructor() :
    BaseViewModelFactory<AdapterNewsViewModel>(AdapterNewsViewModel::class.java) {

    private var viewModel: AdapterNewsViewModel? = null

    override fun createViewModel(): AdapterNewsViewModel {
        return viewModel ?: run {
            val model = AdapterNewsViewModel()
            viewModel = model
            return model
        }
    }
}