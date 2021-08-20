package robin.vitalij.fortniteassitant.ui.shop.upcoming

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.repository.network.GetUpcomingShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class UpcomingShopViewModel(
    private val getUpcomingShopRepository: GetUpcomingShopRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<ItemShopUpcoming>>()

    fun loadData() {
        getUpcomingShopRepository.getUpcomingShop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it.items
            }, error)
            .let(disposables::add)
    }
}