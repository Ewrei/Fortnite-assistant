package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_adapter_comparison_many_account.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.ComparisonManyPlayersStatisticsFragment
import javax.inject.Inject


private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterManyAccountFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AdapterManyAccountViewModelFactory

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private lateinit var viewModel: AdapterManyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_adapter_comparison_many_account, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AdapterManyAccountViewModel::class.java)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.offscreenPageLimit = 3

        addTabs()
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
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
        pagerAdapter.run {
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(false),
                getString(R.string.statistics_title)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(true),
                getString(R.string.other)
            )
        }

        viewPager.adapter = pagerAdapter

    }

    companion object {

        fun newInstance() = AdapterManyAccountFragment()

    }
}