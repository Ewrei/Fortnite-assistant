package robin.vitalij.fortniteassitant.ui.cosmetics.catalog

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CosmeticsViewModel(
    private val catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CosmeticsEntity>>()

    fun loadData(shopType: ShopType) {
        catalogCosmeticsRepository
            .getCosmetics(shopType)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}