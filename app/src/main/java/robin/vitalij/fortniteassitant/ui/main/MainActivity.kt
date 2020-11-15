package robin.vitalij.fortniteassitant.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.activity_main.*
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setupWithNavController

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(
                R.navigation.navigation_home,
                R.navigation.navigation_dashboard,
                R.navigation.navigation_notifications
            )

        val controller = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false
}