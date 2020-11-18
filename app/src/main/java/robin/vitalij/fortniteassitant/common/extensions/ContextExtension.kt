package robin.vitalij.fortniteassitant.common.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.solovyev.android.checkout.Billing
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.R


fun Context.getBilling() =
    Billing(this, object : Billing.DefaultConfiguration() {
        override fun getPublicKey(): String {
            return BuildConfig.BILLING_ID
        }
    })

fun Context.showToast(title: String) {
    Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
}

fun Context?.checkIfNetworkAvailable(): Boolean {
    val connectivityManager =
        this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.showDialog(message: String?) {
    MaterialAlertDialogBuilder(this).setMessage(message)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }.create()
        .show()
}

fun Context.showDialog(@StringRes messageRes: Int) {
    MaterialAlertDialogBuilder(this).setMessage(messageRes)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }.create()
        .show()
}

fun Context.showDialog(icon: Drawable, title: String, message: String) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setIcon(icon)
        .setCancelable(true)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        .create().show()
}

fun Context.showApplicationDialog(
    message: String,
    onPositiveClickListener: DialogInterface.OnClickListener,
    onNegativeClickListener: DialogInterface.OnClickListener,
    onNeutralClickListener: DialogInterface.OnClickListener
) {
    MaterialAlertDialogBuilder(this)
        .setIcon(R.mipmap.ic_launcher_round)
        .setTitle(R.string.app_name)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton(
            this.getString(R.string.app_estimate_ok),
            onPositiveClickListener
        )
        .setNegativeButton(
            this.getString(R.string.app_estimate_no),
            onNegativeClickListener
        )
        .setNeutralButton(
            this.getString(R.string.app_estimate_netral),
            onNeutralClickListener
        )
        .create().show()
}

@SuppressLint("PrivateResource")
fun Context.showDialog(
    message: String,
    onPositiveClickListener: DialogInterface.OnClickListener
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.app_name)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton(this.getString(R.string.yes), onPositiveClickListener)
        .setNegativeButton(this.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        .create().show()
}


@SuppressLint("PrivateResource")
fun Context.showApplicationDialog(
    message: String,
    onPositiveClickListener: DialogInterface.OnClickListener
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.app_name)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton(this.getString(R.string.yes), onPositiveClickListener)
        .setNegativeButton(this.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        .create().show()
}

fun Context?.closeKeyboard(view: View?) {
    val imm: InputMethodManager =
        this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Context?.pxFromDp(dimenRes: Int): Int {
    return if (this == null) 0 else
        (resources.getDimension(dimenRes) * resources.displayMetrics.density).toInt()
}