package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class WeaponResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<WeaponResultViewModel>(WeaponResultViewModel::class.java) {

    private var viewModel: WeaponResultViewModel? = null

    override fun createViewModel(): WeaponResultViewModel {
        return viewModel ?: run {
            val model = WeaponResultViewModel()
            viewModel = model
            return model
        }
    }
}