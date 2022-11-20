package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.model.network.NewsModel
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val fortniteRequestsIOApi: FortniteRequestsIOApi) {

    fun getNews(newsType: NewsType): Flow<LoadingState<List<NewsModel>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getNews(LocaleUtils.locale, newsType.getNewsName()) }
            .onSuccess { emit(LoadingState.Success(it.news)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}