package robin.vitalij.fortniteassitant.ui.crew.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.repository.network.GameCrewRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class GameCrewViewModel(
    private val gameCrewRepository: GameCrewRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CrewModel>>()

    init {
        loadData()
    }

    fun loadData() {
        gameCrewRepository
            .getGameCrew()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}