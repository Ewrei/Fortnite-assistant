package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.repository.RewardedAdRepository
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class SelectedListViewModel(
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val resourceProvider: ResourceProvider,
    val rewardedAdRepository: RewardedAdRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    var buttonName: ObservableField<String> = ObservableField("...")
    var selectedSize = 0

    val data = MutableLiveData<List<PlayerModel>>()

    fun loadData() {
        comparisonListUserRepository.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                selectedSize = it.filter { it.isSelected }.size
                buttonName.set(
                    resourceProvider.getString(
                        R.string.comparison_player_format,
                        selectedSize.getStringFormat()
                    )
                )
                data.value = it
            }, error)
            .let(disposables::add)
    }

    fun updatePlayerModel(playerModel: PlayerModel) {
        comparisonListUserRepository.updateUserModel(playerModel)
    }

    fun deleteFromBasket(playerModel: PlayerModel) {
        comparisonListUserRepository.removeUserModel(playerModel)
    }
}