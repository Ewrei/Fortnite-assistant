package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import robin.vitalij.fortniteassitant.repository.ContactUsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ContactUsResultViewModelFactory @Inject constructor(
    private val contactUsRepository: ContactUsRepository
) : BaseViewModelFactory<ContactUsResultViewModel>(ContactUsResultViewModel::class.java) {

    private var viewModel: ContactUsResultViewModel? = null

    override fun createViewModel(): ContactUsResultViewModel {
        return viewModel ?: run {
            val model = ContactUsResultViewModel(contactUsRepository)
            viewModel = model
            return model
        }
    }
}