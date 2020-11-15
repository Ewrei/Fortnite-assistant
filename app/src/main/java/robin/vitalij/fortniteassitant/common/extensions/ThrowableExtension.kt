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
            errorModel = ErrorModel(
                    R.string.unknown_error,
                    R.drawable.ic_unknown_error,
                    null,
                    true,
                    ParserJsonObject.getErrors(this.response()?.errorBody()?.string())
                )
        }
        is FirebaseFirestoreException -> {
            if(this.code == FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED) {
                errorModel = ErrorModel(
                    R.string.firebase_limit_error,
                    R.drawable.ic_unknown_error,
                    null,
                    true
                )
            } else {
                errorModel = ErrorModel(
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