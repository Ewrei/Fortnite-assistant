package robin.vitalij.fortniteassitant.ui.chartlist.view_pager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterChartsTypeViewModelFactory @Inject constructor() : BaseViewModelFactory<AdapterChartsTypeViewModel>(
    AdapterChartsTypeViewModel::class.java
) {

    private var viewModel: AdapterChartsTypeViewModel? = null

    override fun createViewModel(): AdapterChartsTypeViewModel {
        return viewModel ?: run {
            val model = AdapterChartsTypeViewModel()
            viewModel = model
            return model
        }
    }
}