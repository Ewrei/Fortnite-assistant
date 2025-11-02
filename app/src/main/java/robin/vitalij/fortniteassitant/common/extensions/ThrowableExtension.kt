package robin.vitalij.fortniteassitant.common.extensions

import com.google.firebase.firestore.FirebaseFirestoreException
import retrofit2.HttpException
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.network.ApiError
import robin.vitalij.fortniteassitant.utils.ParserJsonObject
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import java.net.UnknownHostException

fun Throwable?.getErrorModel(isUser: Boolean = false): ErrorModel {
    val errorModel: ErrorModel
    when (this) {
        is UnknownHostException -> {
            errorModel = ErrorModel(
                R.string.network_error,
                R.drawable.ic_wifi,
                null,
                true
            )
        }

        is HttpException -> {
            if (this.code() >= ApiError.SERVER_ERROR.code) {
                errorModel = ErrorModel(
                    R.string.error_internal_server,
                    R.drawable.ic_unknown_error,
                    null,
                    true,
                    null
                )
            } else {
                errorModel = if (isUser) getUserError(this)
                else ErrorModel(
                    R.string.unknown_error,
                    R.drawable.ic_unknown_error,
                    null,
                    true,
                    ParserJsonObject.getError(this.response()?.errorBody()?.string())
                )
            }
        }

        is FirebaseFirestoreException -> {
            errorModel = if (this.code == FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED) {
                ErrorModel(
                    R.string.firebase_limit_error,
                    R.drawable.ic_unknown_error,
                    null,
                    true
                )
            } else {
                ErrorModel(
                    R.string.unknown_error,
                    R.drawable.ic_unknown_error,
                    null,
                    true
                )
            }
        }

        is IllegalArgumentException -> {
            errorModel = ErrorModel(
                R.string.unknown_error,
                R.drawable.ic_unknown_error,
                null,
                true
            )
        }

        else -> {
            errorModel = ErrorModel(
                R.string.unknown_error,
                R.drawable.ic_unknown_error,
                null,
                true
            )
        }
    }
    return errorModel
}

fun Throwable?.getErrorMessage(
    isUser: Boolean = false,
    resourceProvider: ResourceProvider
): ErrorModelListItem.MessageItem {
    val message: String
    when (this) {
        is UnknownHostException -> {
            message = resourceProvider.getString(R.string.network_error)
        }

        is HttpException -> {
            if (this.code() >= ApiError.SERVER_ERROR.code) {
                message = resourceProvider.getString(R.string.error_internal_server)
            } else {
                message = resourceProvider.getString(R.string.unknown_error)
            }
        }

        is FirebaseFirestoreException -> {
            message = if (this.code == FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED) {
                resourceProvider.getString(R.string.firebase_limit_error)
            } else {
                resourceProvider.getString(R.string.unknown_error)
            }
        }

        is IllegalArgumentException -> {
            message = resourceProvider.getString(R.string.unknown_error)
        }

        else -> {
            message = resourceProvider.getString(R.string.unknown_error)
        }
    }
    return ErrorModelListItem.MessageItem(message)
}

private fun getUserError(error: HttpException): ErrorModel {
    return if (error.code() == ApiError.NOT_FOUND.code) {
        ErrorModel(
            R.string.user_empty_stats,
            R.drawable.ic_empty_user,
            null,
            false
        )
    } else {
        ErrorModel(
            R.string.unknown_error,
            R.drawable.ic_unknown_error,
            null,
            true,
            ParserJsonObject.getError(error.response()?.errorBody()?.string())
        )
    }
}