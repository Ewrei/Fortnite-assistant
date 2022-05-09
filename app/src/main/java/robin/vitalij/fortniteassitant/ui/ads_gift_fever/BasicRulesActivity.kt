package robin.vitalij.fortniteassitant.ui.ads_gift_fever

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setFullScreen

class BasicRulesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_no_toolbar)

        if (savedInstanceState == null) {
            replaceFragment(BasicRulesFragment.newInstance())
        }

        window?.setFullScreen(true)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    companion object {

        fun newInstance(context: Context?) = Intent(context, BasicRulesActivity::class.java)

    }
}