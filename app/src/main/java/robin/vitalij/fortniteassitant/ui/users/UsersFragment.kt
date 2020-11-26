package robin.vitalij.fortniteassitant.ui.users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentUsersBinding
import robin.vitalij.fortniteassitant.interfaces.UsersCallback
import robin.vitalij.fortniteassitant.ui.bottomsheet.user.UserResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.users.adapter.UserModel
import robin.vitalij.fortniteassitant.ui.users.adapter.UsersAdapter
import javax.inject.Inject

class UsersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UsersViewModelFactory

    private lateinit var viewModel: UsersViewModel

    private lateinit var dataBinding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_users,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@UsersFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(UsersViewModel::class.java).apply {
                observeToProgressBar(this@UsersFragment, activity = activity as AppCompatActivity)
                observeToProgressBar(this@UsersFragment)
                observeToError(this@UsersFragment)
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

    private fun initAdapter(list: List<UserModel>) {
        recyclerView.run {
            adapter = UsersAdapter { playerId, playerName ->
                UserResultFragment.show(
                    childFragmentManager,
                    playerId,
                    playerName,
                    object : UsersCallback {
                        override fun updateProfile(playerId: String) {
                            viewModel.update(playerId)
                        }

                        override fun switch(playerId: String) {
                            viewModel.switch(playerId)
                            activity?.finish()

                            val intent = Intent(requireContext(), MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    })
            }
            (adapter as UsersAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}