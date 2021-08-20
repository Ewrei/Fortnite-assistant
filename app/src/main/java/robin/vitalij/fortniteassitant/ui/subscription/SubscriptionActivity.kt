package robin.vitalij.fortniteassitant.ui.subscription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment

class SubscriptionActivity : AppCompatActivity() {

    private var subscriptionsFragment = SubscriptionsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        replaceFragment(R.id.container, subscriptionsFragment, subscriptionsFragment.javaClass.simpleName)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        subscriptionsFragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    companion object {
        fun newInstance(context: Context?) = Intent(context, SubscriptionActivity::class.java)
    }
}