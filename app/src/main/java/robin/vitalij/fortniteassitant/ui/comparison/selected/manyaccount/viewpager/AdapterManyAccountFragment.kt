package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentAdapterComparisonManyAccountBinding
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseViewPagerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.ComparisonManyPlayersStatisticsFragment


private const val DEFAULT_LAST_TAB_VALUE = Integer.MAX_VALUE

class AdapterManyAccountFragment : Fragment(R.layout.fragment_adapter_comparison_many_account) {

    private var lastTab: Int = DEFAULT_LAST_TAB_VALUE

    private val binding by viewBinding(FragmentAdapterComparisonManyAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.offscreenPageLimit = 6

        addTabs()
        binding.tabLayout.setupWithViewPager(binding.viewPager)

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

    private fun saveSelectedTab() {
        lastTab = binding.viewPager.currentItem
    }

    private fun restoreSelectedTab() {
        if (lastTab != DEFAULT_LAST_TAB_VALUE) {
            binding.viewPager.currentItem = lastTab
        }
    }

    private fun setListeners() {
        binding.typeStatGroupInclude.allStats.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonManyPlayersStatisticsFragment)?.loadGameType(
                    GameType.ALL
                )
            })
        }

        binding.typeStatGroupInclude.keyboardMouse.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonManyPlayersStatisticsFragment)?.loadGameType(
                    GameType.KEYBOARD_MOUSE
                )
            })
        }

        binding.typeStatGroupInclude.gamepad.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonManyPlayersStatisticsFragment)?.loadGameType(
                    GameType.GAMEPAD
                )
            })
        }

        binding.typeStatGroupInclude.touch.setOnClickListener {
            ((binding.viewPager.adapter as BaseViewPagerAdapter).getItems().forEach {
                (it as? ComparisonManyPlayersStatisticsFragment)?.loadGameType(GameType.TOUCH)
            })
        }
    }

    private fun addTabs() {
        val pagerAdapter = BaseViewPagerAdapter(childFragmentManager)
        pagerAdapter.run {
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.OVERALL,
                    GameType.ALL
                ),
                getString(R.string.overall_battles)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.SOLO,
                    GameType.ALL
                ),
                getString(R.string.solo_battles)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.DUO,
                    GameType.ALL
                ),
                getString(R.string.duo_battles)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.TRIO,
                    GameType.ALL
                ),
                getString(R.string.trio_battles)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.SQUAD,
                    GameType.ALL
                ),
                getString(R.string.squad_battles)
            )
            addFragment(
                ComparisonManyPlayersStatisticsFragment.newInstance(
                    BattlesType.LTM,
                    GameType.ALL
                ),
                getString(R.string.ltm_battles)
            )
        }

        binding.viewPager.adapter = pagerAdapter
    }

    companion object {

        fun newInstance() = AdapterManyAccountFragment()

    }
}