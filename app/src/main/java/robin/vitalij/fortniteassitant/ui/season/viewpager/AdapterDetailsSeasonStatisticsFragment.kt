package robin.vitalij.fortniteassitant.ui.season.viewpager

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterDetailsSeasonBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.season.statistics.DetailsSeasonStatisticsFragment
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject

private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterDetailsSeasonStatisticsFragment :
    Fragment(R.layout.fragment_adapter_details_statistics) {

    @Inject
    lateinit var viewModelFactory: AdapterDetailsSeasonStatisticsViewModelFactory

    private lateinit var viewModel: AdapterDetailsSeasonStatisticsViewModel

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private val binding by viewBinding(FragmentAdapterDetailsSeasonBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        )[AdapterDetailsSeasonStatisticsViewModel::class.java].apply {
            observeToProgressBar(this@AdapterDetailsSeasonStatisticsFragment)
            observeToError(this@AdapterDetailsSeasonStatisticsFragment)
            observeToEmpty(this@AdapterDetailsSeasonStatisticsFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        setNavigation()
        setListeners()

        viewModel.loadData()

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {
            setListeners()
        })
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

    private fun addTabs(detailStatisticsModel: DetailStatisticsModel) {
        val pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
        detailStatisticsModel.battlesTypes.forEach {
            pagerAdapter.addFragment(
                DetailsSeasonStatisticsFragment.newInstance(it, detailStatisticsModel.gameType),
                getString(it.getTitleRes())
            )
        }
        binding.viewPager.adapter = pagerAdapter
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        val adapter =
            GameBattlesAdapter(
                context,
                viewModel.detailsStatistics
            )
        binding.toolbarSpinner.adapter = adapter

        binding.toolbarSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    addTabs(viewModel.detailsStatistics[position])
                }
            }
    }
}