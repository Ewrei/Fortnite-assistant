package robin.vitalij.fortniteassitant.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarActivityController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

fun BaseViewModel.observeToProgressBar(
    lifecycleOwner: LifecycleOwner,
    activity: AppCompatActivity
) {
    activityProgressBarVisibility.observe(lifecycleOwner, { show ->
        (activity as? ProgressBarActivityController)?.showOrHideProgressBar(
            show,
            textActivityVisibility.get() ?: ""
        )
    })
}

fun BaseViewModel.observeToProgressBar(fragment: Fragment) {
    progressBarVisibility.observe(fragment, { show ->
        (fragment as? ProgressBarController)?.showOrHideProgressBar(show)
    })
}

fun BaseViewModel.observeToError(fragment: Fragment) {
    errorVisibility.observe(fragment, {
        if (it) {
            (fragment as? ErrorController)?.setError(errorModel)
        } else if (it == false) {
            (fragment as? ErrorController)?.hideError()
        }
    })
}

fun BaseViewModel.observeSwipeRefresh(fragment: Fragment) {
    showSwipeRefreshLayout.observe(fragment, {
        (fragment as? BaseFragment)?.showSwipeRefreshLayout(it)
    })
}

fun BaseViewModel.observeToEmpty(fragment: Fragment) {
    emptyTextViewVisibility.observe(fragment, {
        (fragment as? BaseFragment)?.setEmptyText(it)
    })
}

fun BaseViewModel.observeToSubscriptionAccess(fragment: Fragment) {
    subscriptionAccessVisibility.observe(fragment, {
        (fragment as? BaseFragment)?.setSubscriptionAccessVisibility(it)
    })
}