package robin.vitalij.fortniteassitant.ui.news.fragment

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.model.network.NewsModel
import robin.vitalij.fortniteassitant.repository.network.NewsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class NewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    var newsType: NewsType = NewsType.STW

    private val newsState =
        MutableStateFlow<LoadingState<List<NewsModel>>>(LoadingState.Loading)

    val newsResult: StateFlow<LoadingState<List<NewsModel>>> = newsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            newsRepository.getNews(newsType).collect { loadingState ->
                newsState.value = loadingState
            }
        }
    }

}