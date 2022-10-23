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
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.FragmentWikiBinding
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.pngread.PngReadDetailsFragment
import javax.inject.Inject


class WikiFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: WikiViewModelFactory

    private lateinit var viewModel: WikiViewModel

    private var _binding: FragmentWikiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWikiBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        binding.battlePassRewards.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_battles_pass_rewards)
        }

        binding.weapons.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_weapon)
        }

        binding.fishing.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_fish)
        }

        binding.achievements.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_achievement)
        }

        binding.cosmeticsNew.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_cosmetics_new)
        }

        binding.cosmetics.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_catalog_cosmetics)
        }

        binding.banners.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_banner)
        }

        binding.vehicles.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_vehicles)
        }

        binding.currentMap.setSafeOnClickListener {
            findNavController().navigate(R.id.navigation_png_read_details, Bundle().apply {
                putString(PngReadDetailsFragment.ARG_IMAGE_URL, getString(R.string.current_map_url))
                putString(PngReadDetailsFragment.ARG_TITLE, getString(R.string.current_map))
            })
        }
    }
}