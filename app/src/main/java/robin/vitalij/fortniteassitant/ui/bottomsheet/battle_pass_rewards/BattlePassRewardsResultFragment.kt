package robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.network.Reward
import robin.vitalij.fortniteassitant.ui.bottomsheet.battle_pass_rewards.BattlePassRewardsResultViewModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.battle_pass_rewards.BattlePassRewardsResultViewModelFactory
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 100

class BattlePassRewardsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsResultViewModelFactory

    private lateinit var viewModel: BattlePassRewardsResultViewModel

    private lateinit var binding: BottomSheetBattlesPassRewardsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        binding = BottomSheetBattlesPassRewardsBinding.inflate(layoutInflater)
        return binding.root
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
            val itemShop = it.getSerializable(ARG_REWARD) as Reward
            binding.imageView.loadImage(itemShop.images.fullBackground)
            binding.name.text = itemShop.name
            binding.description.text = itemShop.description
            binding.quantity.text =
                String.format(getString(R.string.quantity_format, itemShop.quantity))
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
        private const val ARG_REWARD = "arg_reward"

        fun show(
            fragmentManager: FragmentManager,
            reward: Reward
        ) {
            BattlePassRewardsResultFragment().apply {
                arguments = bundleOf(ARG_REWARD to reward)
            }.show(fragmentManager, TAG)
        }
    }
}
