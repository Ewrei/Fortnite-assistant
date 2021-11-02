package robin.vitalij.fortniteassitant.ui.shop.current_new

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem
import robin.vitalij.fortniteassitant.repository.network.GetCurrentShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CurrentShopViewModel(
    private val getCurrentShopRepository: GetCurrentShopRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<ShopNewItem>>()

    fun loadData() {
        getCurrentShopRepository.getCurrentShop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}