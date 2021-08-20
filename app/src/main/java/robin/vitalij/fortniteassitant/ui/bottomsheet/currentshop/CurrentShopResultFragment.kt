package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_current_shop.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.BottomSheetCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.OtherItemsDetails
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter.OtherItemsDetailsAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class CurrentShopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentShopResultViewModelFactory

    private lateinit var viewModel: CurrentShopResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetInternal?.setBackgroundResource(R.drawable.bottomsheet_container_background)
            bottomSheetInternal?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from(it).skipCollapsed = true
            }
        }
        val dataBinding =
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
            val itemShop = it.getSerializable(ITEM_SHOP_CURRENT) as ShopItem
            imageView.loadImage(itemShop.fullBackground)
            name.text = itemShop.name
            description.text = itemShop.description
            price.setValueText(itemShop.price)

            if (itemShop.otherItemsDetails.isNotEmpty()) {
                initAdapter(itemShop.otherItemsDetails)
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

    private fun initAdapter(list: List<OtherItemsDetails>) {
        theKitIncludes.setVisibility(true)
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val widthPixels = displayMetrics.widthPixels * 0.35

        recyclerView.run {
            adapter = OtherItemsDetailsAdapter(
                widthPixels = widthPixels.toInt()
            )
            (adapter as OtherItemsDetailsAdapter).setData(list)
        }
    }

    companion object {

        private const val TAG = "CurrentShopResultFragment"
        private const val ITEM_SHOP_CURRENT = "ItemShopCurrent"

        fun show(
            fragmentManager: FragmentManager?,
            shopUpcoming: ShopItem
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
