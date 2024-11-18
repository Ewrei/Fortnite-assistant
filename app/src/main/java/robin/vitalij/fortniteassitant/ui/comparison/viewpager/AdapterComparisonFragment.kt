package robin.vitalij.fortniteassitant.ui.comparison.viewpager

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.loadImage
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterComparionBinding
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_COMPARISON_DATA_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.statistics.ComparisonStatisticsFragment
import javax.inject.Inject

private const val PLAYER_ONE_ID = "player_one_id"
private const val PLAYER_TWO_ID = "player_two_id"

class AdapterComparisonFragment : Fragment(R.layout.fragment_adapter_comparion) {

    @Inject
    lateinit var viewModelFactory: AdapterComparisonViewModelFactory

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private lateinit var viewModel: AdapterComparisonViewModel

    private lateinit var playerOneId: String
    private lateinit var playerTwoId: String

    private var actionFavoriteMenuItem: MenuItem? = null
    private var favoriteMenuItem: ImageButton? = null

    private var isSchedule: Boolean = false

    private lateinit var pagerAdapter: BaseViewPagerAdapter

    private val binding by viewBinding(FragmentAdapterComparionBinding::bind)

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
        binding.viewPager.offscreenPageLimit = 6

        arguments?.let {
            viewModel.loadData(it.getSerializable(ARG_COMPARISON_DATA_TYPE) as ComparisonDataType)
        }

        viewModel.data.observe(viewLifecycleOwner) { comparisonProfileResponse ->
            setData(comparisonProfileResponse)
            addTabs()
        }

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

                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(0) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(1) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )

                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(2) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(3) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(4) as ComparisonStatisticsFragment).loadSchedule(
                    isSchedule
                )
                ((binding.viewPager.adapter as BaseViewPagerAdapter).getItem(5) as ComparisonStatisticsFragment).loadSchedule(
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

    private fun setData(comparisonProfileResponse: ComparisonProfileResponse) {
        binding.avatarOne.loadImage(comparisonProfileResponse.playerModel.userEntity.avatar)
        binding.avatarTwo.loadImage(comparisonProfileResponse.playerTwoModel.userEntity.avatar)

        binding.nickNameTwo.text = comparisonProfileResponse.playerTwoModel.userEntity.name
        binding.nicknameOne.text = comparisonProfileResponse.playerModel.userEntity.name
    }

    private fun setListeners() {
        binding.typeStatGroupInclude.allStats.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.ALL
                )
            })
        }

        binding.typeStatGroupInclude.keyboardMouse.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.KEYBOARD_MOUSE
                )
            })
        }

        binding.typeStatGroupInclude.gamepad.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(
                    GameType.GAMEPAD
                )
            })
        }

        binding.typeStatGroupInclude.touch.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonStatisticsFragment)?.loadGameType(GameType.TOUCH)
            })
        }
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
            binding.viewPager.adapter = pagerAdapter
        }
    }

    companion object {
        private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

        fun newInstance(
            comparisonDataType: ComparisonDataType
        ) = AdapterComparisonFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_COMPARISON_DATA_TYPE, comparisonDataType)
            }
        }
    }
}