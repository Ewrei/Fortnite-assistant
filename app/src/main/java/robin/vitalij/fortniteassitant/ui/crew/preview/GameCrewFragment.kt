package robin.vitalij.fortniteassitant.ui.crew.preview

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.ui.crew.preview.adapter.GameCrewAdapter
import robin.vitalij.fortniteassitant.ui.news.VideoActivity
import javax.inject.Inject

class GameCrewFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: GameCrewViewModelFactory

    private val viewModel: GameCrewViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private var recyclerViewState: Parcelable? = null

    private val gameCrewAdapter = GameCrewAdapter(onClick = {
        findNavController().navigate(
            GameCrewFragmentDirections.actionNavigationGameCrewToNavigationCrewDetails(
                it.descriptions.title,
                it.rewards.toTypedArray()
            )
        )
    }, onVideoClick = { videoUrl: String, videoName: String ->
        startActivity(VideoActivity.newInstance(context, videoUrl, videoName))
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        setListener()
        setNavigation()

        binding.viewEmptyInclude.empty.setText(R.string.no_information)

        lifecycleScope.launch {
            viewModel.gameCrewsResult.collect {
                handleGameCrewsResult(it)
            }
        }

        viewModel.loadData()
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState =
            binding.recyclerViewInclude.recyclerView.layoutManager?.onSaveInstanceState()
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
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
            adapter = gameCrewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleGameCrewsResult(result: LoadingState<List<CrewModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()

                gameCrewAdapter.updateData(result.data)

                recyclerViewState?.let {
                    binding.recyclerViewInclude.recyclerView.layoutManager?.onRestoreInstanceState(
                        recyclerViewState
                    )
                    recyclerViewState = null
                }
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

}