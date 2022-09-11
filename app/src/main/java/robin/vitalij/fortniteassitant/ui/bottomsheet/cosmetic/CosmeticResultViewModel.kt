package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.repository.db.CosmeticsResultRepository
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CosmeticResultViewModel(
    private val cosmeticsResultRepository: CosmeticsResultRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CosmeticsListItem>>()

    fun loadData(cosmeticsId: String, isCosmeticNew: Boolean) {
        cosmeticsResultRepository
                .getCosmetics(cosmeticsId, isCosmeticNew)
                .observeOn(AndroidSchedulers.mainThread())
                .let(::setupProgressShow)
                .subscribe({
                    mutableLiveData.value = it
                }, error)
                .let(disposables::add)
    }

}