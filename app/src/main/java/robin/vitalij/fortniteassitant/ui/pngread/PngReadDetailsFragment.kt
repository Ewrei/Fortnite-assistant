package robin.vitalij.fortniteassitant.ui.pngread

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.FragmentPngReadBinding

class PngReadDetailsFragment : Fragment(R.layout.fragment_png_read) {

    private val args: PngReadDetailsFragmentArgs by navArgs()

    private val binding by viewBinding(FragmentPngReadBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()

        binding.image.loadImage(args.argImageUrl)
        binding.include.toolbar.title = args.argTitle
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.include.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}