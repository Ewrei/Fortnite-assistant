package robin.vitalij.fortniteassitant.ui.news.fragment

import robin.vitalij.fortniteassitant.repository.network.NewsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModelFactory<NewsViewModel>(NewsViewModel::class.java) {

    private var viewModel: NewsViewModel? = null

    override fun createViewModel(): NewsViewModel {
        return viewModel ?: run {
            val model = NewsViewModel(newsRepository)
            viewModel = model
            return model
        }
    }
}