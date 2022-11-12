package robin.vitalij.fortniteassitant.ui.session.viewpager

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
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.session.statistics.DetailsSessionStatisticsFragment
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject


class AdapterSessionFragment : Fragment(R.layout.fragment_adapter_details_statistics) {

    @Inject
    lateinit var viewModelFactory: AdapterSessionViewModelFactory

    private val viewModel: AdapterSessionViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentAdapterDetailsStatisticsBinding::bind)

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.sessionLastId = it.getLong(ARG_SESSION_LAST_ID)
            viewModel.sessionId = it.getLong(ARG_SESSION_ID)
            viewModel.detailsStatistics =
                it.getParcelableArrayList<DetailStatisticsModel>(
                    AdapterDetailsStatisticsFragment.ARG_DETAIL_STATISTICS
                ) as ArrayList<DetailStatisticsModel>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        initToolbar()
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

    private fun initToolbar() {
        setNavigation()
        arguments?.let {
            binding.toolbar.title = it.getString(ARG_DATE)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun saveSelectedTab() {
        lastTab = binding.viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            binding.viewPager.currentItem = lastTab
        }
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

    private fun addTabs(detailHistoryStatisticsModel: DetailStatisticsModel) {
        val pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
        detailHistoryStatisticsModel.battlesTypes.forEach {
            pagerAdapter.addFragment(
                DetailsSessionStatisticsFragment.newInstance(
                    it,
                    detailHistoryStatisticsModel.gameType,
                    viewModel.sessionId,
                    viewModel.sessionLastId
                ),
                getString(it.getTitleRes())
            )
        }
        binding.viewPager.adapter = pagerAdapter
    }

    companion object {
        const val ARG_SESSION_ID = "arg_session_id"
        const val ARG_SESSION_LAST_ID = "arg_session_last_id"
        const val ARG_DATE = "arg_date"

        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE
    }
}