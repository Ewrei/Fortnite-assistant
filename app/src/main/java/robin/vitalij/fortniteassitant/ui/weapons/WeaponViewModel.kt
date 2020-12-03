package robin.vitalij.fortniteassitant.ui.weapons

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.repository.WeaponRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class WeaponViewModel(
    weaponRepository: WeaponRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<WeaponEntity>>()

    init {
        weaponRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}