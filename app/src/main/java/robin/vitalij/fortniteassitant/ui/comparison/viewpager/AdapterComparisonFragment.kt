package robin.vitalij.fortniteassitant.ui.comparison.viewpager

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_adapter_comparion.*
import kotlinx.android.synthetic.main.layout_type_stats_group.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterComparionBinding
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.COMPARISON_DATA_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.statistics.ComparisonStatisticsFragment
import javax.inject.Inject

private const val PLAYER_ONE_ID = "player_one_id"
private const val PLAYER_TWO_ID = "player_two_id"

class AdapterComparisonFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: AdapterComparisonViewModelFactory

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private lateinit var viewModel: AdapterComparisonViewModel

    private lateinit var dataBinding: FragmentAdapterComparionBinding

    private lateinit var playerOneId: String
    private lateinit var playerTwoId: String

    private var actionFavoriteMenuItem: MenuItem? = null
    private var favoriteMenuItem: ImageButton? = null

    private var isSchedule: Boolean = false

    private lateinit var pagerAdapter: BaseViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_adapter_comparion,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@AdapterComparisonFragment
        dataBinding.viewModel = viewModel
        return dataBinding.contentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AdapterComparisonViewModel::class.java)

        arguments?.let {
            playerOneId = it.getString(PLAYER_ONE_ID, "")
            playerTwoId = it.getString(PLAYER_TWO_ID, "")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.offscreenPageLimit = 6

        arguments?.let {
            viewModel.loadData(it.getSerializable(COMPARISON_DATA_TYPE) as ComparisonDataType)
        }

        viewModel.data.observe(viewLifecycleOwner, {
            dataBinding.model = it
            addTabs()
        })

        setListeners()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_comparation, menu)
        actionFavoriteMenuItem = menu.findItem(R.id.action_filter)
        actionFavoriteMenuItem?.let { it ->
            favoriteMenuItem = it.actionView as ImageButton
            favoriteMenuItem?.setOnClickListener {
                isSchedule = !isSchedule
                favoriteMenuItem?.isSelected = isSchedule

                ((viewPager.adapter as BaseViewPagerAdapter).getItem(0) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((viewPager.adapter as BaseViewPagerAdapter).getItem(1) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )

                ((viewPager.adapter as BaseViewPagerAdapter).getItem(2) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((viewPager.adapter as BaseViewPagerAdapter).getItem(3) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((viewPager.adapter as BaseViewPagerAdapter).getItem(4) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((viewPager.adapter as BaseViewPagerAdapter).getItem(5) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {
        allStats.setOnClickListener {
            ((viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.ALL
                )
            })
        }

        keyboardMouse.setOnClickListener {
            ((viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.KEYBOARD_MOUSE
                )
            })
        }

        gamepad.setOnClickListener {
            ((viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.GAMEPAD
                )
            })
        }

        touch.setOnClickListener {
            ((viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(GameType.TOUCH)
            })
        }
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
        if (!::pagerAdapter.isInitialized) {
            pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId, playerTwoId,
                    BattlesType.OVERALL,
                    GameType.ALL
                ),
                getString(R.string.overall_battles)
            )
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId,
                    playerTwoId,
                    BattlesType.SOLO,
                    GameType.ALL
                ),
                getString(R.string.solo_battles)
            )
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId,
                    playerTwoId,
                    BattlesType.DUO,
                    GameType.ALL
                ),
                getString(R.string.duo_battles)
            )
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId,
                    playerTwoId,
                    BattlesType.TRIO,
                    GameType.ALL
                ),
                getString(R.string.trio_battles)
            )
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId,
                    playerTwoId,
                    BattlesType.SQUAD,
                    GameType.ALL
                ),
                getString(R.string.squad_battles)
            )
            pagerAdapter.addFragment(
                ComparisonStatisticsFragment.newInstance(
                    playerOneId,
                    playerTwoId,
                    BattlesType.LTM,
                    GameType.ALL
                ),
                getString(R.string.ltm_battles)
            )
            viewPager.adapter = pagerAdapter
        }
    }

    companion object {
        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

        fun newInstance(
            comparisonDataType: ComparisonDataType
        ) = AdapterComparisonFragment().apply {
            arguments = Bundle().apply {
                putSerializable(COMPARISON_DATA_TYPE, comparisonDataType)
            }
        }
    }
}