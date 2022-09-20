package robin.vitalij.fortniteassitant.ui.achiviements

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
import robin.vitalij.fortniteassitant.repository.AchievementRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class AchievementsViewModel(
    private val achievementRepository: AchievementRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<AchievementEntity>>()

    fun loadData() {
        achievementRepository
            .loadData()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}