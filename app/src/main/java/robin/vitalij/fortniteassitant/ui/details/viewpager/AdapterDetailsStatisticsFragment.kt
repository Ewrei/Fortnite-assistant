package robin.vitalij.fortniteassitant.ui.details.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_adapter_details_statistics.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.details.statistics.DetailsStatisticsFragment
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject

private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterDetailsStatisticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: AdapterDetailsStatisticsViewModelFactory

    private lateinit var viewModel: AdapterDetailsStatisticsViewModel

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_adapter_details_statistics, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AdapterDetailsStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@AdapterDetailsStatisticsFragment)
                observeToError(this@AdapterDetailsStatisticsFragment)
                observeToEmpty(this@AdapterDetailsStatisticsFragment)
            }

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
        tabLayout.setupWithViewPager(viewPager)
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
        lastTab = viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            viewPager.currentItem = lastTab
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
        viewPager.adapter = pagerAdapter
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        val adapter =
            GameBattlesAdapter(
                context,
                viewModel.detailsStatistics
            )
        toolbarSpinner.adapter = adapter

        toolbarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }
}