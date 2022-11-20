package robin.vitalij.fortniteassitant.ui.top

import android.content.Context
import android.os.Bundle
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
import robin.vitalij.fortniteassitant.common.extensions.saveUser
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.interfaces.TopResultCallback
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.TopResultFragment
import robin.vitalij.fortniteassitant.ui.top.adapter.TopAdapter
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem
import javax.inject.Inject


class TopFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: TopViewModelFactory

    private val viewModel: TopViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private val topAdapter = TopAdapter(onClick = {
        ProfileResultFragment.show(
            childFragmentManager,
            it,
            AvatarType.values().random().getImageUrl(),
            ProfileResultType.FULL,
            object : RegistrationProfileCallback {
                override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                    saveUser(fortniteProfileResponse)
                }

            })
    }, onTopClick = {
        TopResultFragment.show(childFragmentManager,
            viewModel.topType,
            object : TopResultCallback {
                override fun checkTop(topFullModel: TopFullModel) {
                    viewModel.topType = topFullModel
                    viewModel.loadData()
                }
            })
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        setNavigation()
        setListener()

        lifecycleScope.launch {
            viewModel.topResult.collect {
                handleTopResult(it)
            }
        }

        viewModel.loadData()
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
            adapter = topAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleTopResult(result: LoadingState<List<TopListItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                topAdapter.updateData(result.data)
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