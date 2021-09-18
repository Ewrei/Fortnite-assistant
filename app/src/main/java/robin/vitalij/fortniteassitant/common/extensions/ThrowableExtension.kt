package robin.vitalij.fortniteassitant.common.extensions

import com.google.firebase.firestore.FirebaseFirestoreException
import retrofit2.HttpException
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.network.ApiError
import robin.vitalij.fortniteassitant.utils.ParserJsonObject
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
            errorModel = if (isUser) getUserError(this)
            else ErrorModel(
                R.string.unknown_error,
                R.drawable.ic_unknown_error,
                null,
                true,
                ParserJsonObject.getError(this.response()?.errorBody()?.string())
            )
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