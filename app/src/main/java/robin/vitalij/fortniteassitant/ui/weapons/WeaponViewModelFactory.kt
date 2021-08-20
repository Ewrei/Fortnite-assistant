package robin.vitalij.fortniteassitant.ui.weapons

import robin.vitalij.fortniteassitant.repository.WeaponRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class WeaponViewModelFactory @Inject constructor(
    private val weaponRepository: WeaponRepository
) : BaseViewModelFactory<WeaponViewModel>(WeaponViewModel::class.java) {

    private var viewModel: WeaponViewModel? = null

    override fun createViewModel(): WeaponViewModel {
        return viewModel ?: run {
            val model =
                WeaponViewModel(weaponRepository)
            viewModel = model
            return model
        }
    }
}