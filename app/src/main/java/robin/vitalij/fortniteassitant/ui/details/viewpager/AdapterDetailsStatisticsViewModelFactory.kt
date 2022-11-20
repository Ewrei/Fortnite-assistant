package robin.vitalij.fortniteassitant.ui.details.viewpager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterDetailsStatisticsViewModelFactory @Inject constructor() :
    BaseViewModelFactory<AdapterDetailsStatisticsViewModel>(AdapterDetailsStatisticsViewModel::class.java) {

    private var viewModel: AdapterDetailsStatisticsViewModel? = null

    override fun createViewModel(): AdapterDetailsStatisticsViewModel {
        return viewModel ?: run {
            val model = AdapterDetailsStatisticsViewModel()
            viewModel = model
            return model
        }
    }
}