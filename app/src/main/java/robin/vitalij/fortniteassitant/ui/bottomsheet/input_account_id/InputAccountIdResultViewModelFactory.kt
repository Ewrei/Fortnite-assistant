package robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class InputAccountIdResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<InputAccountIdResultViewModel>(InputAccountIdResultViewModel::class.java) {

    private var viewModel: InputAccountIdResultViewModel? = null

    override fun createViewModel(): InputAccountIdResultViewModel = viewModel ?: run {
        val model = InputAccountIdResultViewModel()
        viewModel = model
        return model
    }
}