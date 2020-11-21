package robin.vitalij.fortniteassitant.ui.bottomsheet.profile

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.Profile
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.mapper.ProfileResultMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider


class ProfileResultViewModel(
    private val getUserRepository: GetUserRepository,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val comparisonRepository: ComparisonRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<Profile>>()

    var playerModel: ObservableField<FortniteProfileResponse> = ObservableField()

    lateinit var openCompare: () -> Unit

    fun loadData(accountId: String) {
        getUserRepository.getUser(accountId)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                playerModel.set(it)
                mutableLiveData.value =
                    ProfileResultMapper(resourceProvider).transform(playerModel.get()!!)
            }, {
                errorModelUnit(it.getErrorModel(true))
            })
            .let(disposables::add)
    }

    fun addedUserMode() {
//        playerModel.get()?.let {
//            comparisonListUserRepository.addUserModel(PlayerUserMapper().transform(it))
//        }
    }

    fun compareWithYourself() {
//        playerModel.get()?.let {
//            comparisonRepository.loadOneLocalData(PlayerUserMapper().transform(it))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { openCompare() }, {
//                        // do nothing
//                    })
//        }
    }
}