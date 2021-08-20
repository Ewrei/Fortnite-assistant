package robin.vitalij.fortniteassitant.ui.news.fragment

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.model.network.NewsModel
import robin.vitalij.fortniteassitant.repository.network.NewsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class NewsViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<NewsModel>>()

    fun loadData(newsType: NewsType) {
        newsRepository.getNews(newsType.getNewsName())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }

}