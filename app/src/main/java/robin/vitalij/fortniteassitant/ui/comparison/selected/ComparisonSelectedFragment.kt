package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.closeKeyboard
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.SelectedListUserActivity
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import robin.vitalij.fortniteassitant.utils.view.SelectedComparisonImageView
import javax.inject.Inject

class ComparisonSelectedFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ComparisonSelectedViewModelFactory

    private lateinit var viewModel: ComparisonSelectedViewModel

    private var selectedComparisonImageView: SelectedComparisonImageView? = null

    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_comparison_selected, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ComparisonSelectedViewModel::class.java).apply {
                observeToProgressBar(this@ComparisonSelectedFragment)
                observeToError(this@ComparisonSelectedFragment)

                openProfile = {
                    ProfileResultFragment.show(
                        childFragmentManager,
                        it,
                        ProfileResultType.FULL,
                        object : RegistrationProfileCallback {
                            override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                                viewModel.saveUser(fortniteProfileResponse)
                            }

                        })
                }

                openMainScreen = {
                    activity?.finish()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        viewModel.initOwner(this)

        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_friends, menu)
        val filterItem = menu.findItem(R.id.action_list_user)
        filterItem?.let { item ->
            selectedComparisonImageView = item.actionView as SelectedComparisonImageView
            selectedComparisonImageView?.setOnClickListener {
                startActivity(SelectedListUserActivity.getSelectedListUser(requireContext()))
            }
            viewModel.loadPlayerComparisonSize()
        }

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchItem.actionView = searchView
        initSearch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer{
            it?.let(::initAdapter)
        })


        viewModel.mutableSizeLiveData.observe(viewLifecycleOwner, Observer{
            selectedComparisonImageView?.setFilterSize(it)
        })

        setListener()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {
        recyclerView.setOnTouchListener { v, event ->
            context.closeKeyboard(view)
            false
        }
    }

    private fun initSearch() {
        searchView?.setOnCloseListener {
            viewModel.mutableLiveData.value = arrayListOf()
            false
        }

  //      searchView?.setOnQueryTextListener(
//            DebouncingQueryTextListener(
//                this@ComparisonSelectedFragment.lifecycle
//            ) { text ->
//                text?.let {
//                    if (it.isEmpty()) {
//                        viewModel.mutableLiveData.value = arrayListOf()
//                    }
//                    if (it.length >= resources.getInteger(R.integer.min_length)) {
//                        viewModel.searchPlayer(it)
//                    }
//                }
//            }
  //      )
    }

    private fun initAdapter(list: List<SearchSteamUser>) {
        recyclerView.run {
            adapter = SearchAdapter() {
                ProfileResultFragment.show(
                    childFragmentManager,
                    it.accountId,
                    ProfileResultType.FULL,
                    object : RegistrationProfileCallback {
                        override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                            viewModel.saveUser(fortniteProfileResponse)
                        }

                    })
            }
            layoutManager = LinearLayoutManager(context)
            (adapter as SearchAdapter).setData(list)
        }
    }

    companion object {

        fun newInstance() = ComparisonSelectedFragment()
    }
}