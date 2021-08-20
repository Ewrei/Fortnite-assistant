package robin.vitalij.fortniteassitant.ui.cosmetics

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CatalogCosmeticsViewModel(
    private val catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<ShopType>>()

    init {
        loadData()
    }

    fun loadData() {
        catalogCosmeticsRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}