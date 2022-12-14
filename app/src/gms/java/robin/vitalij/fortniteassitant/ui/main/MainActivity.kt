package robin.vitalij.fortniteassitant.ui.main

import android.content.Intent
import android.content.IntentSender
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import kotlinx.android.synthetic.gms.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.common.extensions.setupWithNavController
import robin.vitalij.fortniteassitant.common.extensions.showApplicationDialog
import robin.vitalij.fortniteassitant.databinding.ActivityMainBinding
import robin.vitalij.fortniteassitant.interfaces.ProgressBarActivityController
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.ads_gift_fever.BasicRulesActivity
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import java.util.*
import javax.inject.Inject

private const val SEVENTH_ENTRANCE = 7
private const val RC_APP_UPDATE = 999
const val ONE_WEEK = 605000000

class MainActivity : AppCompatActivity(), ProgressBarActivityController {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    lateinit var viewModel: MainViewModel

    var binding: ActivityMainBinding? = null

    private var currentNavController: LiveData<NavController>? = null

    private var appUpdateManager: AppUpdateManager? = null
    private var inAppUpdateType = 0
    private var appUpdateInfoTask: Task<AppUpdateInfo>? = null
    private lateinit var installStateUpdatedListener: InstallStateUpdatedListener

    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            initInterstitialAd()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)

        setContentView(R.layout.activity_main)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(MainViewModel::class.java)
                .apply {
                    observeToProgressBar(this@MainActivity, activity = this@MainActivity)

                    openMainScreen = {
                        finish()
                        startActivity(
                            Intent(this@MainActivity, MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }

                    openFirebaseDynamicLink = { firebaseDynamicLinkType: FirebaseDynamicLinkType,
                                                id: String ->
                        when (firebaseDynamicLinkType) {
                            FirebaseDynamicLinkType.USER -> {
                                ProfileResultFragment.show(
                                    supportFragmentManager,
                                    id,
                                    AvatarType.values().random().getImageUrl(),
                                    ProfileResultType.FULL,
                                    object : RegistrationProfileCallback {
                                        override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                                            saveUser(fortniteProfileResponse)
                                        }

                                    })

                                viewModel.clearFirebaseDynamicLink()
                            }
                        }
                    }
                }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = viewModel

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        loading_container.setVisibility(false)

        initEstimate()
        initAppUpdateManager()
        openSubscriptionDialog()
        initBanner()

        viewModel.checkFirebaseDynamicLink()

        if(viewModel.preferenceManager.getShowBasicRulesDate() < Date()) {
            startActivity(BasicRulesActivity.newInstance(this))
            viewModel.preferenceManager.setShowBasicRulesDate(Date(Date().time + SEVEN_DAY))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onResume() {
        super.onResume()
        currentNavController?.value?.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        currentNavController?.value?.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(
                R.navigation.navigation_home,
                R.navigation.navigation_history_match,
                R.navigation.navigation_top,
                R.navigation.navigation_shop,
                R.navigation.navigation_setting
            )

        val controller = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent,
            changeNavigationTab = {
                initInterstitialAd()
            }
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false

    override fun showOrHideProgressBar(show: Boolean, title: String) {
        loading_container.setVisibility(show)
        loadTitle.setVisibility(title.isNotBlank())
        loadTitle.text = title
    }

    fun onDisplayButtonClicked(getAnWard: () -> Unit) {
        if (viewModel.rewardedAdRepository.isLoadVideo()) {
            viewModel.rewardedAdRepository.showReward(this) {
                val date = Date()
                val instance = Calendar.getInstance()
                instance.time = date
                instance.add(Calendar.DAY_OF_MONTH, 2)
                viewModel.preferenceManager.setSubscriptionAccess(instance.time.time)
                getAnWard()
            }
        }
    }

    fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        viewModel.saveUser(fortniteProfileResponse)
    }

    private fun initBanner() {
        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time) {
            customBannerView.setVisibility(false)
        } else {
            customBannerView.setVisibility(true)
            customBannerView.startBanner(getString(R.string.BANNER_ID), this)
        }
    }

    private fun initInterstitialAd() {
        viewModel.interstitialAdRepository.showInterstitial(this)
    }

    private fun initEstimate() {
        if (viewModel.preferenceManager.getIsEstimate() && viewModel.preferenceManager.getEstimate() % SEVENTH_ENTRANCE == 0) {
            this.showApplicationDialog(
                getString(R.string.play_market_body),
                onPositiveClickListener = { dialog, _ ->
                    viewModel.preferenceManager.setIsEstimate(false)
                    dialog.cancel()
                    val address =
                        Uri.parse(getString(R.string.application_url))
                    startActivity(Intent(Intent.ACTION_VIEW, address))
                },
                onNegativeClickListener = { dialog, _ ->
                    viewModel.preferenceManager.setIsEstimate(false)
                    dialog.cancel()
                },
                onNeutralClickListener = { dialog, _ -> dialog.cancel() }
            )
        }
    }

    private fun initAppUpdateManager() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        installStateUpdatedListener =
            InstallStateUpdatedListener { installState: InstallState ->
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        appUpdateManager?.registerListener(installStateUpdatedListener)
        inAppUpdateType = AppUpdateType.FLEXIBLE
        inAppUpdate()
    }

    private fun inAppUpdate() {
        try {
            appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(inAppUpdateType)
                ) {
                    try {
                        appUpdateManager?.startUpdateFlowForResult(
                            appUpdateInfo,
                            inAppUpdateType,
                            this@MainActivity,
                            RC_APP_UPDATE
                        )
                    } catch (ignored: IntentSender.SendIntentException) {
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        try {
            val snackbar = Snackbar.make(
                findViewById(R.id.container),
                getString(R.string.downloaded_update_snackbar),
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction(getString(R.string.install)) {
                appUpdateManager?.completeUpdate()
            }
            snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            snackbar.show()
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
    }

    private fun openSubscriptionDialog() {
        if (!viewModel.preferenceManager.getIsSubscription() &&
            (Date().time - ONE_WEEK) > viewModel.preferenceManager.getSubscribeDialogTime()
        ) {
            if (viewModel.preferenceManager.getSubscribeDialogTime() != 0L) {
                startActivity(SubscriptionActivity.newInstance(this))
            }
            viewModel.preferenceManager.setSubscribeDialogTime(Date().time)
        }
    }

    companion object {
        const val SEVEN_DAY = 60000 * 60 * 24 * 7
    }
}