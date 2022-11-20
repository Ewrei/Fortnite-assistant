package robin.vitalij.fortniteassitant.common.extensions

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import robin.vitalij.fortniteassitant.databinding.ViewErrorBinding
import robin.vitalij.fortniteassitant.model.ErrorModel

fun ViewErrorBinding.setErrorView(errorModel: ErrorModel) {
    this.errorView.isVisible = true

    errorView.setVisibility(true)
    errorText.text =
        if (errorModel.errors != null) errorModel.errors.error else this.root.context.getString(
            errorModel.textResourceId
        )
    errorImage.setImageDrawable(
        ContextCompat.getDrawable(
            this.root.context,
            errorModel.imageResourceId
        )
    )
    errorModel.descriptionResourceId?.let {
        errorDescription.setVisibility(true)
        errorDescription.setText(errorModel.descriptionResourceId)
    }

    errorResolveButton.setVisibility(errorModel.isButtonVisible)

    this.errorResolveButton.setVisibility(errorModel.isButtonVisible)

}