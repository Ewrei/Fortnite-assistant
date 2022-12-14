package robin.vitalij.fortniteassitant.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash.*
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ActivitySplashBinding
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.SearchActivity
import timber.log.Timber
import javax.inject.Inject

private const val DELAY_MILLIS = 2000L

private const val FIREBASE_DYNAMIC_LINK_USER = "user"
private const val FIREBASE_DYNAMIC_PATCH_SIZE = 2

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        FortniteApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(SplashViewModel::class.java)
                .apply {
                    isUserAuthorised().observe(this@SplashActivity, {
                        Handler().postDelayed({
                            if (!isSync) {
                                isSync = true
                                val intent = if (it) {
                                    Intent(
                                        this@SplashActivity,
                                         MainActivity::class.java
                                    )
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                } else {
                                    Intent(this@SplashActivity, SearchActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                startActivity(intent)
                                finish()
                            }
                        }, DELAY_MILLIS)
                    })
                }

        val dataBinding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        version.text = getString(R.string.version_format, BuildConfig.VERSION_NAME)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel

        handleDeepLink()
    }

    private fun handleDeepLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    deepLink?.let { uri ->
                        openDeeplink(uri)
                    }
                }
            }
            .addOnFailureListener(this) { e ->
                Timber.tag("SplashActivity").d("getDynamicLink:onFailure: $e")
            }
    }

    private fun openDeeplink(deepLink: Uri) {
        val links = deepLink.pathSegments

        if (links.size == FIREBASE_DYNAMIC_PATCH_SIZE) {
            val id = links.last()
            when {
                links.first().equals(FIREBASE_DYNAMIC_LINK_USER) -> {
                    viewModel.setFirebaseDynamicLink(FirebaseDynamicLinkType.USER, id)
                }
            }
        }
    }
}