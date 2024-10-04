package robin.vitalij.fortniteassitant.ui.ads_gift_fever

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setFullScreen
import robin.vitalij.fortniteassitant.databinding.ActivityBaseNoToolbarBinding

class BasicRulesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseNoToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseNoToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addFragment(BasicRulesFragment.newInstance())
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

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    companion object {

        fun newInstance(context: Context?) = Intent(context, BasicRulesActivity::class.java)

    }
}