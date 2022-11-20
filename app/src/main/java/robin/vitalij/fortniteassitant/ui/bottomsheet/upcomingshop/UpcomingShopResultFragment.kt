package robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetUpcomingShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import javax.inject.Inject

class UpcomingShopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: UpcomingShopResultViewModelFactory

    private val viewModel: UpcomingShopResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetUpcomingShopBinding? = null

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        _binding = BottomSheetUpcomingShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.itemShopUpcoming =
                it.getSerializable(ARG_ITEM_SHOP_UPCOMING) as ItemShopUpcoming
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUI() {
        binding.imageView.loadImage(viewModel.itemShopUpcoming.images.fullBackground)
        binding.imageView.loadBackgroundRarity(viewModel.itemShopUpcoming.rarity.id)
        binding.name.text = viewModel.itemShopUpcoming.name
        binding.description.text = viewModel.itemShopUpcoming.description
        binding.price.setValueText(viewModel.itemShopUpcoming.price)

        binding.addedDate.text =
            getString(R.string.add_date_format, viewModel.itemShopUpcoming.added.date)
        binding.addVersion.text = viewModel.itemShopUpcoming.added.version
    }

    companion object {

        private const val TAG = "UpcomingShopResultFragment"
        private const val ARG_ITEM_SHOP_UPCOMING = "arg_item_shop_upcoming"

        private const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(fragmentManager: FragmentManager, itemShopUpcoming: ItemShopUpcoming) {
            UpcomingShopResultFragment().apply {
                arguments = bundleOf(ARG_ITEM_SHOP_UPCOMING to itemShopUpcoming)
            }.show(fragmentManager, TAG)
        }
    }

}
