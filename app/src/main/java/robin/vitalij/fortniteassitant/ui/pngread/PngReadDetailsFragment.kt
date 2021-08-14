package robin.vitalij.fortniteassitant.ui.pngread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.FragmentPngReadBinding
import robin.vitalij.fortniteassitant.ui.common.BaseFragment

class PngReadDetailsFragment : BaseFragment() {

    private var _binding: FragmentPngReadBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPngReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()

        arguments?.let {
            binding.image.loadImage(it.getString(IMAGE_URL))
            binding.include.toolbar.title = it.getString(TITLE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.include.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    companion object {
        const val IMAGE_URL = "image_url"
        const val TITLE = "title"
    }
}