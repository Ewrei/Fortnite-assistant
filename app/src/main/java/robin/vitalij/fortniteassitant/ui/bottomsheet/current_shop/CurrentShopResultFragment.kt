package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.getScreenWidth
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.BottomSheetCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter.OtherItemsDetailsAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200
private const val WIDTH_PIXELS_PERCENT = 0.35

class CurrentShopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentShopResultViewModelFactory

    private lateinit var viewModel: CurrentShopResultViewModel

    private lateinit var dataBinding: BottomSheetCurrentShopBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        dataBinding =
            DataBindingUtil.inflate<BottomSheetCurrentShopBinding>(
                inflater,
                R.layout.bottom_sheet_current_shop,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@CurrentShopResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CurrentShopResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemShop = it.getSerializable(ITEM_SHOP_CURRENT) as ShopNewItem
            dataBinding.imageView.loadImage(itemShop.displayAssets.first().fullBackground)
            dataBinding.imageView.loadBackgroundRarity(itemShop.rarity.id)
            dataBinding.name.text = itemShop.displayName
            dataBinding.description.text = itemShop.displayDescription
            dataBinding.price.setValueText(itemShop.price.finalPrice)

            if (itemShop.granted.size > 1) {
                initAdapter(itemShop.granted)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initAdapter(list: List<GrantedModel>) {
        dataBinding.theKitIncludes.setVisibility(true)
        dataBinding.recyclerView.run {
            adapter = OtherItemsDetailsAdapter(
                widthPixels = activity?.getScreenWidth(WIDTH_PIXELS_PERCENT) ?: 0
            )
            (adapter as OtherItemsDetailsAdapter).setData(list)
        }
    }

    companion object {

        private const val TAG = "CurrentShopResultFragment"
        private const val ITEM_SHOP_CURRENT = "ItemShopCurrent"

        fun show(
            fragmentManager: FragmentManager?,
            shopUpcoming: ShopNewItem
        ) {
            fragmentManager?.let {
                CurrentShopResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ITEM_SHOP_CURRENT, shopUpcoming)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
