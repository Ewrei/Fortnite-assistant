package robin.vitalij.fortniteassitant.ui.top

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
import robin.vitalij.fortniteassitant.common.extensions.saveUser
import robin.vitalij.fortniteassitant.databinding.FragmentTopBinding
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.interfaces.TopResultCallback
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.TopResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.top.adapter.TopAdapter
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem
import javax.inject.Inject


class TopFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: TopViewModelFactory

    private lateinit var viewModel: TopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding: FragmentTopBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_top,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@TopFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(TopViewModel::class.java).apply {
                observeToProgressBar(this@TopFragment)
                observeToError(this@TopFragment)
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
        setListener()

        viewModel.loadData()
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

    private fun initAdapter(list: List<TopListItem>) {
        recyclerView.run {
            adapter = TopAdapter(onClick = {
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
                    viewModel.topType.get() ?: TopFullModel(),
                    object : TopResultCallback {
                        override fun checkTop(topFullModel: TopFullModel) {
                            viewModel.topType.set(topFullModel)
                            viewModel.loadData()
                        }
                    })
            })
            (adapter as TopAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}