package robin.vitalij.fortniteassitant.ui.battle_pass_rewards

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentBattlePassRewardsBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.enums.BattlePassSortedType
import robin.vitalij.fortniteassitant.ui.battle_pass_rewards.adapter.BattlesPassRewardsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards.BattlePassRewardsResultFragment
import robin.vitalij.fortniteassitant.utils.view.CustomTypeFaceSpan
import javax.inject.Inject


class BattlePassRewardsFragment : Fragment(R.layout.fragment_battle_pass_rewards) {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsViewModelFactory

    private val viewModel: BattlePassRewardsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentBattlePassRewardsBinding::bind)

    private val battlesPassRewardsAdapter = BattlesPassRewardsAdapter {
        BattlePassRewardsResultFragment.show(
            childFragmentManager,
            it.reward
        )
    }.apply {
        setHasStableIds(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setListeners()
        setToolbarMenu()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.filterResult.collect {
                handleBattlesPassRewardsResult(it)
            }
        }

        lifecycleScope.launch {
            viewModel.seasonsResult.collect {
                binding.seasonSpinner.setItems(it)
            }
        }

        viewModel.loadData()
    }

    private fun setToolbarMenu() {
        binding.toolbarInclude.toolbar.inflateMenu(R.menu.menu_sorting_action)

        binding.toolbarInclude.toolbar.menu.findItem(R.id.action_sort).setOnMenuItemClickListener {
            showPopup(binding.toolbarInclude.toolbar)
            true
        }
    }

    private fun showPopup(view: View) {
        context?.let { it ->
            val popup = PopupMenu(it, view)
            popup.gravity = Gravity.END
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu_sort_achiviement, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_all -> {
                        viewModel.filterBattlesPassReward(BattlePassSortedType.ALL)
                        true
                    }

                    R.id.action_paid -> {
                        viewModel.filterBattlesPassReward(BattlePassSortedType.PAID)
                        true
                    }

                    R.id.action_free -> {
                        viewModel.filterBattlesPassReward(BattlePassSortedType.FREE)
                        true
                    }
                }
                false
            }
            popup.show()

            val menu: Menu = popup.menu
            for (i in 0 until menu.size()) {
                val mi = menu.getItem(i)
                applyFontToMenuItem(mi)
            }
        }
    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        try {
            val font = ResourcesCompat.getFont(requireContext(), R.font.futura_pt_medium)
            val mNewTitle = SpannableString(mi.title)
            mNewTitle.setSpan(
                CustomTypeFaceSpan("", font!!, Color.WHITE),
                0,
                mNewTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            mi.title = mNewTitle
        } catch (it: Throwable) {
            //do nothing
        }
    }

    private fun setListeners() {
        binding.seasonSpinner.setOnItemSelectedListener { _, _, _, item ->
            viewModel.currentSeason = (item as SeasonModel).season.toString()
            viewModel.loadData()
        }

        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = battlesPassRewardsAdapter
            layoutManager = GridLayoutManager(activity, MAX_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = BATTLES_PASS_REWARDS_SPAN_COUNT
                }
            }
        }
    }

    private fun handleBattlesPassRewardsResult(result: LoadingState<List<BattlesPassRewardsModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.viewErrorInclude.errorView.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false
            }

            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = true

                battlesPassRewardsAdapter.submitList(result.data)
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()
            }

            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.viewErrorInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        private const val MAX_SPAN_COUNT = 2
        private const val BATTLES_PASS_REWARDS_SPAN_COUNT = 1

    }

}