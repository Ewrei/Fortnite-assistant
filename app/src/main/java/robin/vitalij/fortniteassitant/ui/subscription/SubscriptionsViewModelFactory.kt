package robin.vitalij.fortniteassitant.ui.subscription

import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SubscriptionsViewModelFactory @Inject constructor(private val preferenceManager: PreferenceManager) :
    BaseViewModelFactory<SubscriptionsViewModel>(
        SubscriptionsViewModel::class.java
    ) {

    private var viewModel: SubscriptionsViewModel? = null

    override fun createViewModel(): SubscriptionsViewModel {
        return viewModel ?: run {
            val model =
                SubscriptionsViewModel(
                    preferenceManager
                )
            viewModel = model
            return model
        }
    }
}