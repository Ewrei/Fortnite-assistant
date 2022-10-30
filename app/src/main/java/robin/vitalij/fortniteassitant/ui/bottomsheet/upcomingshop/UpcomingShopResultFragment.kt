package robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_upcoming_shop.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetUpcomingShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class UpcomingShopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: UpcomingShopResultViewModelFactory

    private lateinit var viewModel: UpcomingShopResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.initBottomSheetInternal()
        val dataBinding =
            DataBindingUtil.inflate<BottomSheetUpcomingShopBinding>(
                inflater,
                R.layout.bottom_sheet_upcoming_shop,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@UpcomingShopResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(UpcomingShopResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemShop = it.getSerializable(ITEM_SHOP_UPCOMING) as ItemShopUpcoming
            imageView.loadImage(itemShop.images.fullBackground)
            imageView.loadBackgroundRarity(itemShop.rarity.id)
            name.text = itemShop.name
            description.text = itemShop.description
            price.setValueText(itemShop.price)

            addedDate.text = getString(R.string.add_date_format, itemShop.added.date)
            addVersion.text = itemShop.added.version
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    companion object {

        private const val TAG = "UpcomingShopResultFragment"
        private const val ITEM_SHOP_UPCOMING = "ItemShopUpcoming"

        fun show(
            fragmentManager: FragmentManager?,
            itemShopUpcoming: ItemShopUpcoming
        ) {
            fragmentManager?.let {
                UpcomingShopResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ITEM_SHOP_UPCOMING, itemShopUpcoming)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
