package robin.vitalij.fortniteassitant.ui.crew.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.ui.crew.details.adapter.GameCrewViewDetailsAdapter
import javax.inject.Inject

class CrewViewDetailsFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: CrewViewDetailsModelFactory

    private val viewModel: CrewViewDetailsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private val gameCrewViewDetailsAdapter = GameCrewViewDetailsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.crewRewardsModels.addAll(it.getParcelableArrayList(ARG_CREW_REWARDS_MODEL)!!)
            viewModel.toolbarTitle = it.getString(ARG_NAME) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        initializeRecyclerView()

        viewModel.toolbarTitle = viewModel.toolbarTitle
        gameCrewViewDetailsAdapter.updateData(viewModel.crewRewardsModels)
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = gameCrewViewDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        const val ARG_NAME = "name"
        const val ARG_CREW_REWARDS_MODEL = "crew_rewards_model"

    }
}