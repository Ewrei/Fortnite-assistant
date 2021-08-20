package robin.vitalij.fortniteassitant.ui.crew.main

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
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.crew.details.CrewViewDetailsFragment
import robin.vitalij.fortniteassitant.ui.crew.main.adapter.GameCrewAdapter
import robin.vitalij.fortniteassitant.ui.news.VideoActivity
import java.util.*
import javax.inject.Inject

class GameCrewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CrewViewModelFactory

    private lateinit var viewModel: GameCrewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(GameCrewViewModel::class.java).apply {
                observeToProgressBar(this@GameCrewFragment)
                observeToError(this@GameCrewFragment)
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

        setListener()
        setNavigation()
    }

    private fun setListener() {
        setErrorResolveButtonClick {
            viewModel.loadData()
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<CrewModel>) {
        recyclerView.run {
            adapter = GameCrewAdapter(onClick = {
                val bundle = Bundle().apply {
                    putString(CrewViewDetailsFragment.NAME, it.descriptions.title)
                    putParcelableArrayList(
                        CrewViewDetailsFragment.CREW_REWARDS_MODEL,
                        it.rewards as ArrayList<CrewRewardsModel>
                    )
                }

                findNavController().navigate(R.id.navigation_crew_details, bundle)
            },
                onVideoClick = { videoUrl: String, videoName: String ->
                    startActivity(VideoActivity.newInstance(context, videoUrl, videoName))
                })
            (adapter as GameCrewAdapter).setData(list)

            layoutManager = LinearLayoutManager(context)
        }
    }
}