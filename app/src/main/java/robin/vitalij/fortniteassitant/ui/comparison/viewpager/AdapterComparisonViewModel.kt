package robin.vitalij.fortniteassitant.ui.comparison.viewpager

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class AdapterComparisonViewModel(
    private val comparisonRepository: ComparisonRepository
) : BaseViewModel() {

    var data = MutableLiveData<ComparisonProfileResponse>()

    @SuppressLint("CheckResult")
    fun loadData(comparisonDataType: ComparisonDataType) {
        comparisonRepository.loadData(comparisonDataType)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                data.value = it
            }, error)
            .let(disposables::add)
    }
}