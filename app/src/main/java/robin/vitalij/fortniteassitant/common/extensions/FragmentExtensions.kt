package robin.vitalij.fortniteassitant.common.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.main.MainActivity

private const val TELEGRAM_APP_NAME = "org.telegram.messenger"

fun Fragment.setToolbarTitle(@StringRes titleRes: Int) {
    (activity as AppCompatActivity).setToolbarTitle(getString(titleRes))
}

fun Fragment.saveUser(fortniteProfileResponse: FortniteProfileResponse) {
    (activity as? MainActivity)?.saveUser(fortniteProfileResponse)
}

fun FragmentActivity.intentTelegram(url: String?) {
    val isAppInstalled = isAppAvailable(applicationContext, TELEGRAM_APP_NAME)
    if (isAppInstalled) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(TELEGRAM_APP_NAME)
        startActivity(intent)
    } else {
        Toast.makeText(this, getString(R.string.message_no_set_telegram), Toast.LENGTH_SHORT).show()
    }
}

fun FragmentActivity.intentGmail(gmail: String) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", gmail, null
        )
    )
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CS:GO Assistant")
    startActivity(Intent.createChooser(emailIntent, null))
}

fun FragmentActivity.intentVk(vkUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(vkUrl)
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }
}

private fun isAppAvailable(
    context: Context,
    appName: String
): Boolean {
    val pm = context.packageManager
    return try {
        pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}