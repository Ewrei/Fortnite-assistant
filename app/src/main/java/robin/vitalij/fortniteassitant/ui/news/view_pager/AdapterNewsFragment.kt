package robin.vitalij.fortniteassitant.ui.news.view_pager

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterShopBinding
import robin.vitalij.fortniteassitant.interfaces.AdapterNewsCallback
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.news.fragment.NewsFragment
import javax.inject.Inject

class AdapterNewsFragment : Fragment(R.layout.fragment_adapter_shop), AdapterNewsCallback {

    @Inject
    lateinit var viewModelFactory: AdapterNewsViewModelFactory

    private val viewModel: AdapterNewsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentAdapterShopBinding::bind)

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT

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

    override fun onVideoClick(videoUrl: String, videoName: String) {
        findNavController().navigate(
            AdapterNewsFragmentDirections.actionNavigationNewsToNavigationVideo(
                videoName,
                videoUrl
            )
        )
    }

    private fun saveSelectedTab() {
        lastTab = binding.viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            binding.viewPager.currentItem = lastTab
        }
    }

    private fun addTabs() {
        val pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.BR_NEWS, this),
            getString(NewsType.BR_NEWS.getTitleRes())
        )

        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.STW, this),
            getString(NewsType.STW.getTitleRes())
        )

        pagerAdapter.addFragment(
            NewsFragment.newInstance(NewsType.CREATIVE_NEWS, this),
            getString(NewsType.CREATIVE_NEWS.getTitleRes())
        )

        binding.viewPager.adapter = pagerAdapter
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    companion object {
        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE
        private const val OFF_SCREEN_PAGE_LIMIT = 3

    }

}