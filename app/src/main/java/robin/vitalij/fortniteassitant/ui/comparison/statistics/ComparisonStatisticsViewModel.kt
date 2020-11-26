package robin.vitalij.fortniteassitant.ui.comparison.statistics

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonProfileRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.utils.mapper.ComparisonPlayersMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ComparisonStatisticsViewModel(
    private val comparisonProfileRepository: ComparisonProfileRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    var isSchedule: Boolean = false

    lateinit var playerOneId: String
    lateinit var playerTwoId: String
    lateinit var battlesType: BattlesType
    lateinit var gameType: GameType

    private var profileResponse: ComparisonProfileResponse? = null

    var data = MutableLiveData<List<ComparisonPlayer>>()

    @SuppressLint("CheckResult")
    fun loadData(isSchedule: Boolean) {
        comparisonProfileRepository.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                profileResponse = it
                loadSchedule(isSchedule)
            }, error)
            .let(disposables::add)
    }

    fun loadSchedule(isSchedule: Boolean) {
        this.isSchedule = isSchedule
        profileResponse?.let {
            data.value = ComparisonPlayersMapper(resourceProvider, isSchedule, battlesType, gameType).transform(it)
        }
    }
}
