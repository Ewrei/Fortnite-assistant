package robin.vitalij.fortniteassitant.ui.battle_pass_rewards

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentBattlePassRewardsBinding
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.enums.BattlePassSortedType
import robin.vitalij.fortniteassitant.ui.battle_pass_rewards.adapter.BattlesPassRewardsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards.BattlePassRewardsResultFragment
import robin.vitalij.fortniteassitant.utils.view.CustomTypeFaceSpan
import javax.inject.Inject


class BattlePassRewardsFragment : Fragment(R.layout.fragment_battle_pass_rewards),
    ProgressBarController,
    ErrorController {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsViewModelFactory

    private lateinit var viewModel: BattlePassRewardsViewModel

    private lateinit var binding: FragmentBattlePassRewardsBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBattlePassRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(BattlePassRewardsViewModel::class.java).apply {
                observeToProgressBar(this@BattlePassRewardsFragment)
                observeToError(this@BattlePassRewardsFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            battlesPassRewardsAdapter.updateData(it)
        }

        viewModel.mutableSeasonLiveData.observe(viewLifecycleOwner) {
            binding.seasonSpinner.setItems(it)
        }

        setNavigation()
        setListeners()
        setToolbarMenu()
        initializeRecyclerView()
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
                        viewModel.sortedBattlesPassReward(BattlePassSortedType.ALL)
                        true
                    }

                    R.id.action_paid -> {
                        viewModel.sortedBattlesPassReward(BattlePassSortedType.PAID)
                        true
                    }

                    R.id.action_free -> {
                        viewModel.sortedBattlesPassReward(BattlePassSortedType.FREE)
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
            viewModel.changeSeason((item as SeasonModel).season.toString())
        }

        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.changeSeason("current")
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
            layoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = BATTLES_PASS_REWARDS_SPAN_COUNT
                }
            }
        }
    }

    override fun setError(errorModel: ErrorModel) {
        binding.viewErrorInclude.setErrorView(errorModel)
    }

    override fun hideError() {
        binding.viewErrorInclude.errorView.isVisible = false
    }

    override fun showOrHideProgressBar(show: Boolean) {
        binding.progressViewInclude.progressContainer.isVisible = show
    }

    companion object {
        private const val MAX_SPAN_COUNT = 2
        private const val BATTLES_PASS_REWARDS_SPAN_COUNT = 1

    }

}