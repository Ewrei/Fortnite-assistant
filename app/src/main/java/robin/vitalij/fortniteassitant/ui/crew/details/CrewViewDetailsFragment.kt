package robin.vitalij.fortniteassitant.ui.crew.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.crew.details.adapter.GameCrewViewDetailsAdapter
import java.util.*
import javax.inject.Inject

class CrewViewDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CrewViewDetailsModelFactory

    private lateinit var viewModel: CrewViewDetailsViewModel

    private var _binding: FragmentRecyclerViewWithToolbarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewWithToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CrewViewDetailsViewModel::class.java).apply {
                observeToProgressBar(this@CrewViewDetailsFragment)
                observeToError(this@CrewViewDetailsFragment)
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

        arguments?.let {
            viewModel.mutableLiveData.value = it.getParcelableArrayList(CREW_REWARDS_MODEL)
            binding.toolbarInclude.toolbar.title = it.getString(NAME)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<CrewRewardsModel>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = GameCrewViewDetailsAdapter()
            (adapter as GameCrewViewDetailsAdapter).setData(list)

            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        const val NAME = "name"
        const val CREW_REWARDS_MODEL = "crew_rewards_model"
    }
}