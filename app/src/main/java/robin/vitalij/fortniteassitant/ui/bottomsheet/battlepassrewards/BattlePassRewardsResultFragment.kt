package robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards

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
import kotlinx.android.synthetic.main.bottom_sheet_battles_pass_rewards.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.BottomSheetBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.network.Reward
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class BattlePassRewardsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsResultViewModelFactory

    private lateinit var viewModel: BattlePassRewardsResultViewModel

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
            DataBindingUtil.inflate<BottomSheetBattlesPassRewardsBinding>(
                inflater,
                R.layout.bottom_sheet_battles_pass_rewards,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@BattlePassRewardsResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(BattlePassRewardsResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemShop = it.getSerializable(REWARD) as Reward
            imageView.loadImage(itemShop.images.fullBackground)
            name.text = itemShop.name
            description.text = itemShop.description
            quantity.text = String.format(getString(R.string.quantity_format, itemShop.quantity))
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

        private const val TAG = "BattlePassRewardsResultFragment"
        private const val REWARD = "Reward"

        fun show(
            fragmentManager: FragmentManager?,
            reward: Reward
        ) {
            fragmentManager?.let {
                BattlePassRewardsResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(REWARD, reward)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
