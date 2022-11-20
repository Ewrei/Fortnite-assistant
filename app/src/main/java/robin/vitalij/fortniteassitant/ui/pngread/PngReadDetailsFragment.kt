package robin.vitalij.fortniteassitant.ui.pngread

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.FragmentPngReadBinding

class PngReadDetailsFragment : Fragment(R.layout.fragment_png_read) {

    private val binding by viewBinding(FragmentPngReadBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()

        arguments?.let {
            binding.image.loadImage(it.getString(ARG_IMAGE_URL))
            binding.include.toolbar.title = it.getString(ARG_TITLE)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.include.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    companion object {
        const val ARG_IMAGE_URL = "arg_image_url"
        const val ARG_TITLE = "arg_title"
    }
}