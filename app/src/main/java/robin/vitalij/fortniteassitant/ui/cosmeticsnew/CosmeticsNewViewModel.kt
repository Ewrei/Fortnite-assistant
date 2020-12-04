package robin.vitalij.fortniteassitant.ui.cosmeticsnew

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.repository.CosmeticsNewRepositoryRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CosmeticsNewViewModel(
    private val cosmeticsNewRepositoryRepository: CosmeticsNewRepositoryRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CosmeticsNewEntity>>()

    init {
        loadData()
    }

    fun loadData() {
        cosmeticsNewRepositoryRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}