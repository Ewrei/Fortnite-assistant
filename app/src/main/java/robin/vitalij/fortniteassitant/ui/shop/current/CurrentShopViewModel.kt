package robin.vitalij.fortniteassitant.ui.shop.current

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.repository.network.GetCurrentShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.top.adapter.TopUserModel

class CurrentShopViewModel(
    private val getCurrentShopRepository: GetCurrentShopRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CurrentShopImpl>>()

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