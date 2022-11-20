package robin.vitalij.fortniteassitant.ui.details.viewpager

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterDetailsStatisticsBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.details.statistics.DetailsStatisticsFragment
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject

class AdapterDetailsStatisticsFragment : Fragment(R.layout.fragment_adapter_details_statistics) {

    @Inject
    lateinit var viewModelFactory: AdapterDetailsStatisticsViewModelFactory

    private val viewModel: AdapterDetailsStatisticsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentAdapterDetailsStatisticsBinding::bind)

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.detailsStatistics =
                it.getParcelableArrayList<DetailStatisticsModel>(ARG_DETAIL_STATISTICS) as ArrayList<DetailStatisticsModel>
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
                DetailsStatisticsFragment.newInstance(it, detailStatisticsModel.gameType),
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
        val adapter = GameBattlesAdapter(context, viewModel.detailsStatistics)
        binding.toolbarSpinner.adapter = adapter

        binding.toolbarSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //do nothing
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

    companion object {
        const val ARG_DETAIL_STATISTICS = "arg_detail_statistics"

        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE
    }

}