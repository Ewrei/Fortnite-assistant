package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.utils.mapper.ComparisonManyAccountsStatisticsMapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class ComparisonManyPlayersStatisticsViewModel(
    private val resourceProvider: ResourceProvider,
    private val comparisonListUserRepository: ComparisonListUserRepository
) : BaseViewModel() {

    var data = MutableLiveData<List<ComparisonManyPlayers>>()

    lateinit var battlesType: BattlesType
    lateinit var gameType: GameType

    @SuppressLint("CheckResult")
    fun loadData() {
        comparisonListUserRepository.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                data.value = ComparisonManyAccountsStatisticsMapper(
                    resourceProvider,
                    battlesType,
                    gameType
                ).transform(it.filter { it.isSelected })
            }, error)
            .let(disposables::add)
    }
}