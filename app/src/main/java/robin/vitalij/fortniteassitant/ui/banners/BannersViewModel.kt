package robin.vitalij.fortniteassitant.ui.banners

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.repository.BannerRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class BannersViewModel(
    private val bannerRepository: BannerRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<BannerEntity>>()

    init {
        loadData()
    }

    fun loadData() {
        bannerRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}