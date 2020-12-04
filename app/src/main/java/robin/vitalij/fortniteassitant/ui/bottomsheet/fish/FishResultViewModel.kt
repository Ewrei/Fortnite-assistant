package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class FishResultViewModel(
    private val fishRepository: FishRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<FishEntity>>()

    fun loadData(fishId: String) {
        fishRepository
            .loadData(fishId)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = arrayListOf(it)
            }, error)
            .let(disposables::add)
    }

}