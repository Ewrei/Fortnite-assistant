package robin.vitalij.fortniteassitant.ui.wiki

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.FragmentWikiBinding


class WikiFragment : Fragment(R.layout.fragment_wiki) {

    private val binding by viewBinding(FragmentWikiBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setListeners()
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
            findNavController().navigate(
                WikiFragmentDirections.actionNavigationWikiToNavigationPngReadDetails(
                    getString(R.string.current_map_url),
                    getString(R.string.current_map)
                )
            )
        }
    }
}