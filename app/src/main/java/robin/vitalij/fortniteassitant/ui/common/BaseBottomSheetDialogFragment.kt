package robin.vitalij.fortniteassitant.ui.common

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.progress_view.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.ErrorModel

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(),
    ProgressBarController, ErrorController {

    override fun showOrHideProgressBar(show: Boolean) {
        loading_container.setVisibility(show)
    }

    override fun setError(
        errorModel: ErrorModel
    ) {
        context?.let { context ->
            errorView.setVisibility(true)
            errorText.text = context.getString(
                errorModel.textResourceId
            )
            /*    if (errorModel.errors.isNotEmpty()) errorModel.errors.getMessage() else context.getString(
                    errorModel.textResourceId
                )*/
            errorImage.setImageDrawable(context.getDrawable(errorModel.imageResourceId))
            errorModel.descriptionResourceId?.let {
                errorDescription.setVisibility(true)
                errorDescription.setText(errorModel.descriptionResourceId)
            }

            errorResolveButton.setVisibility(errorModel.isButtonVisible)
        }
    }

    fun setErrorResolveButtonClick(onClick: () -> Unit) {
        errorResolveButton.setOnClickListener {
            onClick()
            hideError()
        }
    }

    override fun hideError() {
        errorView?.setVisibility(false)
    }
}