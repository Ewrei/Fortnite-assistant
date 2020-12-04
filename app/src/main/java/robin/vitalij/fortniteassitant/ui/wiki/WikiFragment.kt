package robin.vitalij.fortniteassitant.ui.wiki

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_wiki.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject


class WikiFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: WikiViewModelFactory

    private lateinit var viewModel: WikiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_wiki, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(WikiViewModel::class.java).apply {
                observeToProgressBar(this@WikiFragment)
                observeToError(this@WikiFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setListeners()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        battlePassRewards.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_battles_pass_rewards)
        }

        weapons.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_weapon)
        }

        fishing.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_fish)
        }

        achievements.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_achievement)
        }
    }
}