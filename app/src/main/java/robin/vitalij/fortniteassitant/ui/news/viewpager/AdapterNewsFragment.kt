package robin.vitalij.fortniteassitant.ui.news.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_adapter_shop.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.news.fragment.NewsFragment
import javax.inject.Inject

private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterNewsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: AdapterNewsViewModelFactory

    private lateinit var viewModel: AdapterNewsViewModel

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_adapter_shop, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AdapterNewsViewModel::class.java).apply {
                observeToProgressBar(this@AdapterNewsFragment)
                observeToError(this@AdapterNewsFragment)
                observeToEmpty(this@AdapterNewsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 3
        setNavigation()
        addTabs()
    }

    override fun onResume() {
        super.onResume()
        restoreSelectedTab()
    }

    override fun onPause() {
        super.onPause()
        saveSelectedTab()
    }

    private fun saveSelectedTab() {
        lastTab = viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            viewPager.currentItem = lastTab
        }
    }

    private fun addTabs() {
        val pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.BR_NEWS),
            getString(NewsType.BR_NEWS.getTitleRes())
        )

        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.STW),
            getString(NewsType.STW.getTitleRes())
        )

        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.CREATIVE_NEWS),
            getString(NewsType.CREATIVE_NEWS.getTitleRes())
        )

        viewPager.adapter = pagerAdapter
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}