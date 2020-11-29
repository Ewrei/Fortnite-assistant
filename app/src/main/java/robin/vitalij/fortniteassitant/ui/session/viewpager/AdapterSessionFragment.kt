package robin.vitalij.fortniteassitant.ui.session.viewpager

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
import kotlinx.android.synthetic.main.fragment_adapter_details_statistics.toolbar
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.session.statistics.DetailsSessionStatisticsFragment
import robin.vitalij.fortniteassitant.utils.view.GameBattlesAdapter
import javax.inject.Inject

private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterSessionFragment : BaseFragment() {

    private var lastTab: Int =
        DEFAULT_LAST_TAB_VALUE

    @Inject
    lateinit var viewModelFactory: AdapterSessionViewModelFactory

    private lateinit var viewModel: AdapterSessionViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AdapterSessionViewModel::class.java).apply {
                observeToProgressBar(this@AdapterSessionFragment)
                observeToError(this@AdapterSessionFragment)
                observeToEmpty(this@AdapterSessionFragment)
            }

        arguments?.let {
            viewModel.sessionLastId = it.getLong(SESSION_LAST_ID)
            viewModel.sessionId = it.getLong(SESSION_ID)
            viewModel.detailsStatistics =
                it.getParcelableArrayList<DetailStatisticsModel>(
                    AdapterDetailsStatisticsFragment.DETAIL_STATISTICS
                ) as ArrayList<DetailStatisticsModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_adapter_details_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout.setupWithViewPager(viewPager)
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
            toolbar.title = it.getString(DATE)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun saveSelectedTab() {
        lastTab = viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            viewPager.currentItem = lastTab
        }
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
        viewPager.adapter = pagerAdapter
    }

    companion object {
        const val SESSION_ID = "session_id"
        const val SESSION_LAST_ID = "session_last_id"
        const val DATE = "date"
    }
}