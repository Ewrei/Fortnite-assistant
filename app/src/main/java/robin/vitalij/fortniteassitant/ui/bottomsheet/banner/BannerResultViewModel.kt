package robin.vitalij.fortniteassitant.ui.bottomsheet.banner

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.repository.BannerRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class BannerResultViewModel(
    private val bannerRepository: BannerRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<BannerEntity>>()

    fun loadData(bannerId: String) {
        bannerRepository
            .getBannerId(bannerId)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = arrayListOf(it)
            }, error)
            .let(disposables::add)
    }

}