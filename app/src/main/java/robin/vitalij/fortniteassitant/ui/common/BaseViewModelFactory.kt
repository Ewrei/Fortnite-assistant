package robin.vitalij.fortniteassitant.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory<T : ViewModel>(private val classVM: Class<T>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        validateClass(modelClass)
        return createViewModel() as T
    }

    private fun <T : ViewModel?> validateClass(modelClass: Class<T>) {
        require(modelClass.isAssignableFrom(classVM)) { "Unknown ViewModel class" }
    }

    abstract fun createViewModel(): T
}