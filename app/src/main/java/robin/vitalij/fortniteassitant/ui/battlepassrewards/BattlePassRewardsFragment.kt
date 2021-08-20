package robin.vitalij.fortniteassitant.ui.battlepassrewards

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_battle_pass_rewards.*
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.battlepassreward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.model.battlepassreward.SeasonModel
import robin.vitalij.fortniteassitant.model.enums.BattlePassSortedType
import robin.vitalij.fortniteassitant.ui.battlepassrewards.adapter.BattlesPassRewardsAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards.BattlePassRewardsResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.utils.view.CustomTypeFaceSpan
import javax.inject.Inject


private const val MAX_SPAN_COUNT = 2

class BattlePassRewardsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsViewModelFactory

    private lateinit var viewModel: BattlePassRewardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_battle_pass_rewards, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(BattlePassRewardsViewModel::class.java).apply {
                observeToProgressBar(this@BattlePassRewardsFragment)
                observeToError(this@BattlePassRewardsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })

        viewModel.mutableSeasonLiveData.observe(viewLifecycleOwner, {
            seasonSpinner.setItems(it)
        })

        setNavigation()
        setListeners()
        setToolbarMenu()
    }

    private fun setToolbarMenu() {
        toolbar.inflateMenu(R.menu.menu_sorting_action)

        toolbar.menu.findItem(R.id.action_sort).setOnMenuItemClickListener {
            showPopup(toolbar)
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

            val menu: Menu = popup.getMenu()
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
        seasonSpinner.setOnItemSelectedListener { _, _, _, item ->
            viewModel.loadData((item as SeasonModel).season.toString())
        }

        errorResolveButton.setOnClickListener {
            viewModel.loadData("current")
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<BattlesPassRewardsModel>) {
        recyclerView.run {
            adapter = BattlesPassRewardsAdapter {
                BattlePassRewardsResultFragment.show(
                    childFragmentManager,
                    it.reward
                )
            }.apply {
                setHasStableIds(true)
            }
            (adapter as BattlesPassRewardsAdapter).setData(list)

            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = 1
            }

            layoutManager = gridlayoutManager
        }
    }
}