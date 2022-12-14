package robin.vitalij.fortniteassitant.ui.common

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.progress_view.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_empty.empty
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_no_subscription.*
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.EmptyTextModel
import robin.vitalij.fortniteassitant.model.ErrorModel

abstract class BaseFragment : Fragment(), ProgressBarController, ErrorController {

    override fun showOrHideProgressBar(show: Boolean) {
        progressContainer.setVisibility(show)
    }

    override fun setError(
        errorModel: ErrorModel
    ) {
        context?.let { context ->
            errorView.setVisibility(true)
            errorText.text =
                if (errorModel.errors != null) errorModel.errors.error else context.getString(
                    errorModel.textResourceId
                )
            errorImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    errorModel.imageResourceId
                )
            )
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

    fun showSwipeRefreshLayout(show: Boolean) {
        // swipeRefreshLayout?.isRefreshing = show
    }

    fun setEmptyText(emptyTextModel: EmptyTextModel) {
        emptyView.setVisibility(emptyTextModel.isVisibility)
        empty.text = emptyTextModel.emptyText
    }

    fun setSubscriptionAccessVisibility(isVisibility: Boolean) {
        noSubscriptionView.setVisibility(isVisibility)
    }
}