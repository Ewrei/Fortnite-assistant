package robin.vitalij.fortniteassitant.ui.season.viewpager

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.common.extensions.converterUserEntity
import robin.vitalij.fortniteassitant.common.extensions.getDetailStatisticsModelList
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.repository.network.GetSeasonStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class AdapterDetailsSeasonStatisticsViewModel(
    private val getSeasonStatisticsRepository: GetSeasonStatisticsRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<DetailStatisticsModel>>()

    var detailsStatistics: MutableList<DetailStatisticsModel> = mutableListOf()

    fun loadData() {
        getSeasonStatisticsRepository.getSeasonStats(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                val userEntity = it.converterUserEntity()
                detailsStatistics = userEntity.getDetailStatisticsModelList()
                getSeasonStatisticsRepository.seasonStats.onNext(userEntity)
                mutableLiveData.value = detailsStatistics
            }, error)
            .let(disposables::add)
    }

}