package robin.vitalij.fortniteassitant.model

sealed class LoadingState<out T> {
    object Loading : LoadingState<Nothing>()
    data class Error(val cause: ErrorModelListItem) : LoadingState<Nothing>()
    data class Success<T>(val data: T) : LoadingState<T>()
}