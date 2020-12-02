package robin.vitalij.fortniteassitant.ui.battlepassrewards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.battlepassreward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.ui.battlepassrewards.adapter.BattlesPassRewardsAdapter
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
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

        setNavigation()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<BattlesPassRewardsModel>) {
        recyclerView.run {
            adapter = BattlesPassRewardsAdapter {}
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