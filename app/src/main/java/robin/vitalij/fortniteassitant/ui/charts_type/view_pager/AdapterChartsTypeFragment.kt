package robin.vitalij.fortniteassitant.ui.charts_type.view_pager

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
import robin.vitalij.fortniteassitant.interfaces.ChartsTypeCallback
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.chartlist.ChartsTypeFragment
import robin.vitalij.fortniteassitant.ui.charts.ChartsFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.BATTLES_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.GAME_TYPE
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject

class AdapterChartsTypeFragment : Fragment(R.layout.fragment_adapter_details_statistics),
    ChartsTypeCallback {

    @Inject
    lateinit var viewModelFactory: AdapterChartsTypeViewModelFactory

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private val viewModel: AdapterChartsTypeViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentAdapterDetailsStatisticsBinding::bind)

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
        initAdapter()
        setNavigation()
        setListener()
    }

    override fun onResume() {
        super.onResume()
        restoreSelectedTab()
    }

    override fun onPause() {
        super.onPause()
        saveSelectedTab()
    }

    override fun chartsTypeClick(
        chartsType: ChartsType,
        battlesType: BattlesType,
        gameType: GameType,
    ) {
        findNavController().navigate(R.id.navigation_charts, Bundle().apply {
            putSerializable(ChartsFragment.ARG_CHARTS_TYPE, chartsType)
            putSerializable(BATTLES_TYPE, battlesType)
            putSerializable(GAME_TYPE, gameType)
        })
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
                ChartsTypeFragment.newInstance(it, detailStatisticsModel.gameType, this),
                getString(it.getTitleRes())
            )
        }
        binding.viewPager.adapter = pagerAdapter
    }

    private fun initAdapter() {
        val adapter = GameBattlesAdapter(
            context,
            viewModel.detailsStatistics
        )
        binding.toolbarSpinner.adapter = adapter
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListener() {
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

    companion object {
        const val ARG_DETAIL_STATISTICS = "arg_detail_statistics"

        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

    }
}