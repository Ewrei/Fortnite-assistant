package robin.vitalij.fortniteassitant.ui.shop.viewpager

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
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.shop.current.CurrentShopFragment
import robin.vitalij.fortniteassitant.ui.shop.upcoming.UpcomingShopFragment
import javax.inject.Inject

class AdapterShoppingFragment : Fragment(R.layout.fragment_adapter_shop) {

    @Inject
    lateinit var viewModelFactory: AdapterShoppingViewModelFactory

    private val viewModel: AdapterShoppingViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentAdapterShopBinding::bind)

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
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
            CurrentShopFragment(),
            getString(R.string.current_shop)
        )

        pagerAdapter.addFragment(
            UpcomingShopFragment(),
            getString(R.string.upcoming_shop)
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
    }

}