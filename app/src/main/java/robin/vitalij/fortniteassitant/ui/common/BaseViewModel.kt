package robin.vitalij.fortniteassitant.ui.common

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.EmptyTextModel
import robin.vitalij.fortniteassitant.model.ErrorModel
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()

    val emptyTextViewVisibility = MutableLiveData<EmptyTextModel>()

    val errorVisibility = MutableLiveData<Boolean>()

    val message = MutableLiveData<Any>()

    val progressBarVisibility = MutableLiveData<Boolean>()

    val activityProgressBarVisibility = MutableLiveData<Boolean>()

    val textActivityVisibility = ObservableField<String>()

    val showSwipeRefreshLayout = MutableLiveData<Boolean>()

    val subscriptionAccessVisibility = MutableLiveData<Boolean>()

    lateinit var errorModel: ErrorModel

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    protected val error: (t: Throwable) -> Unit = { error ->
        handleError(error)
    }

    protected val errorModelUnit: (error: ErrorModel) -> Unit = {
        hideProgressBar()
        errorModel = it
        errorVisibility.value = true
    }

    protected val emptyText: (emptyTextModel: EmptyTextModel) -> Unit = { emptyTextModel ->
        emptyTextViewVisibility.setValue(emptyTextModel)
    }

    protected fun <T> setupActivityProgressShow(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { activityProgressBarVisibility.value = true }
            .doOnNext { activityProgressBarVisibility.value = false }
            .doOnComplete { activityProgressBarVisibility.value = false }
            .doOnError { activityProgressBarVisibility.value = false }
            .doOnDispose { activityProgressBarVisibility.value = false }
    }

    protected fun <T> setupProgressShow(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { showProgressBar() }
            .doOnNext { hideProgressBar() }
            .doOnComplete { hideProgressBar() }
            .doOnError { hideProgressBar() }
            .doOnDispose { hideProgressBar() }
    }

    protected fun <T> setupProgressShow(single: Single<T>): Single<T> {
        return single.doOnSubscribe { showProgressBar() }
            .doOnSuccess { hideProgressBar() }
            .doOnError { hideProgressBar() }
            .doOnDispose { hideProgressBar() }
    }

    protected fun <T> setupProgressShow(flowable: Flowable<T>): Flowable<T> {
        return flowable.doOnSubscribe { showProgressBar() }
            .doOnNext { hideProgressBar() }
            .doFinally { hideProgressBar() }
    }

    protected fun <T> setupSwipeRefresh(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { showSwipeRefresh() }
            .doOnNext { hideSwipeRefresh() }
            .doOnComplete { hideSwipeRefresh() }
            .doOnError { hideSwipeRefresh() }
            .doOnDispose { hideSwipeRefresh() }
    }

    protected inline fun <reified T> handleResponse(
        response: Response<T>,
        noinline onError: ((json: String) -> Unit)? = null,
        onSuccessful: (body: T) -> Unit
    ) {
        if (response.isSuccessful) {
            onSuccessful(response.body() as T)
            return
        }
        response.errorBody()?.string()
            ?.apply { onError?.invoke(this) }
            //  ?.let(ErrorMessageParser::parse)
            ?.let(message::setValue)
    }

    private fun handleError(error: Throwable) {
        Timber.e(error)
        hideProgressBar()
        errorModel = error.getErrorModel()
        errorVisibility.value = true
    }

    private fun hideProgressBar() {
        progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        progressBarVisibility.value = true
    }

    private fun hideSwipeRefresh() {
        showSwipeRefreshLayout.value = false
    }

    private fun showSwipeRefresh() {
        showSwipeRefreshLayout.value = true
    }
}