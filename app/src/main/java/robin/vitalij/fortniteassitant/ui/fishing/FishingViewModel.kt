package robin.vitalij.fortniteassitant.ui.fishing

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class FishingViewModel(
    private val fishRepository: FishRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<FishEntity>>()

    init {
        loadData()
    }

    fun loadData() {
        fishRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}