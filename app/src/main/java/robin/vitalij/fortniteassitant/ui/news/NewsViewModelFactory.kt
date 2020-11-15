package robin.vitalij.fortniteassitant.ui.news

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
) : BaseViewModelFactory<NewsViewModel>(
    NewsViewModel::class.java
) {

    private var viewModel: NewsViewModel? = null

    override fun createViewModel(): NewsViewModel {
        return viewModel ?: run {
            val model =
                NewsViewModel(
                )
            viewModel = model
            return model
        }
    }
}