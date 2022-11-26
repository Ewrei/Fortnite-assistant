package robin.vitalij.fortniteassitant.ui.bottomsheet.battle_pass_rewards

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
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.network.Reward
import javax.inject.Inject

class BattlePassRewardsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BattlePassRewardsResultViewModelFactory

    private val viewModel: BattlePassRewardsResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetBattlesPassRewardsBinding? = null

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.reward = it.getSerializable(ARG_REWARD) as Reward
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        _binding = BottomSheetBattlesPassRewardsBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.imageView.loadImage(viewModel.reward.images.fullBackground)
        binding.name.text = viewModel.reward.name
        binding.description.text = viewModel.reward.description
        binding.quantity.text =
            String.format(getString(R.string.quantity_format, viewModel.reward.quantity))
    }

    companion object {
        private const val BOTTOM_SHEET_MARGIN_TOP = 200

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
