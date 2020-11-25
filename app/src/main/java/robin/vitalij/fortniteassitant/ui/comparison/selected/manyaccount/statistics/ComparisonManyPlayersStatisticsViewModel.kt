package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.utils.mapper.ComparisonManyAccountsStatisticsMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ComparisonManyPlayersStatisticsViewModel(
    private val resourceProvider: ResourceProvider,
    private val comparisonListUserRepository: ComparisonListUserRepository
) : BaseViewModel() {

    var data = MutableLiveData<List<ComparisonManyPlayers>>()

    @SuppressLint("CheckResult")
    fun loadData(isOther: Boolean) {
        comparisonListUserRepository.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                data.value = ComparisonManyAccountsStatisticsMapper(
                    resourceProvider, isOther
                ).transform(it.filter { it.isSelected })
            }, error)
            .let(disposables::add)
    }
}