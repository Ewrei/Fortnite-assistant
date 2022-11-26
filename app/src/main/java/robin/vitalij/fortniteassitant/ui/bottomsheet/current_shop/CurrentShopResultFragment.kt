package robin.vitalij.fortniteassitant.ui.bottomsheet.current_shop

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.getScreenWidth
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.current_shop.adapter.OtherItemsDetailsAdapter
import javax.inject.Inject

class CurrentShopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentShopResultViewModelFactory

    private val viewModel: CurrentShopResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetCurrentShopBinding? = null

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
        _binding = BottomSheetCurrentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.shopNewItem = it.getSerializable(ITEM_SHOP_CURRENT) as ShopNewItem
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
        binding.imageView.loadImage(viewModel.shopNewItem.displayAssets.first().fullBackground)
        binding.imageView.loadBackgroundRarity(viewModel.shopNewItem.rarity.id)
        binding.name.text = viewModel.shopNewItem.displayName
        binding.description.text = viewModel.shopNewItem.displayDescription
        binding.price.setValueText(viewModel.shopNewItem.price.finalPrice)

        if (viewModel.shopNewItem.granted.size > 1) {
            initAdapter(viewModel.shopNewItem.granted)
        }
    }

    private fun initAdapter(list: List<GrantedModel>) {
        binding.theKitIncludes.isVisible = true
        binding.recyclerView.run {
            adapter = OtherItemsDetailsAdapter(
                widthPixels = activity?.getScreenWidth(WIDTH_PIXELS_PERCENT) ?: 0
            )
            (adapter as OtherItemsDetailsAdapter).setData(list)
        }
    }

    companion object {
        private const val BOTTOM_SHEET_MARGIN_TOP = 200
        private const val WIDTH_PIXELS_PERCENT = 0.35

        private const val TAG = "CurrentShopResultFragment"
        private const val ITEM_SHOP_CURRENT = "ItemShopCurrent"

        fun show(
            fragmentManager: FragmentManager,
            shopUpcoming: ShopNewItem
        ) {
            CurrentShopResultFragment().apply {
                arguments = bundleOf(ITEM_SHOP_CURRENT to shopUpcoming)
            }.show(fragmentManager, TAG)
        }
    }
}
