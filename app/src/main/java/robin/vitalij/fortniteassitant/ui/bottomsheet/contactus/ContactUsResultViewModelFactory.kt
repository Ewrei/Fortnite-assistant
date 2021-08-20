package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class ContactUsResultViewModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<ContactUsResultViewModel>(ContactUsResultViewModel::class.java) {

    private var viewModel: ContactUsResultViewModel? = null

    override fun createViewModel(): ContactUsResultViewModel {
        return viewModel ?: run {
            val model = ContactUsResultViewModel(resourceProvider)
            viewModel = model
            return model
        }
    }
}