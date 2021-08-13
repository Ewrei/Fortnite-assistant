package robin.vitalij.fortniteassitant.ui.vehicles

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.repository.network.GameVehiclesRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class VehiclesViewModel(
    private val gameVehiclesRepository: GameVehiclesRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<VehicleModel>>()

    init {
        loadData()
    }

    fun loadData() {
        gameVehiclesRepository
            .getGameCrew()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}