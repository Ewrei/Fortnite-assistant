package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.ContactUsRepository

class ContactUsResultViewModel(
    private val contactUsRepository: ContactUsRepository
) : ViewModel() {

    var isContactUs: Boolean = false

    private val contactUsState =
        MutableStateFlow<LoadingState<List<ContactUsModel>>>(LoadingState.Loading)

    val contactUsResult: StateFlow<LoadingState<List<ContactUsModel>>> = contactUsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            contactUsRepository.getData(isContactUs).collect { loadingState ->
                contactUsState.value = loadingState
            }
        }
    }

}