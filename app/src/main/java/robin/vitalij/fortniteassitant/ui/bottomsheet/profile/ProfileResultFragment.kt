package robin.vitalij.fortniteassitant.ui.bottomsheet.profile

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.showDialog
import robin.vitalij.fortniteassitant.databinding.BottomSheetProfileBinding
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.ProfileAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.ProfileListItem
import robin.vitalij.fortniteassitant.ui.common.BaseBottomSheetDialogFragment
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment.Companion.ARG_PROFILE_RESULT_TYPE
import javax.inject.Inject

const val ACCOUNT_ID = "account_id"
const val BOTTOM_SHEET_MARGIN_TOP = 200

class ProfileResultFragment : BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ProfileResultViewModelFactory

    private lateinit var viewModel: ProfileResultViewModel

    private var registrationProfileCallback: RegistrationProfileCallback? = null

    private lateinit var binding: BottomSheetProfileBinding

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
        binding = BottomSheetProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ProfileResultViewModel::class.java).apply {
                observeToProgressBar(this@ProfileResultFragment)
                observeToError(this@ProfileResultFragment)

                openCompare = {
                    dismiss()
                    startActivity(
                        ComparisonActivity.getComparisonActivityIntent(
                            requireContext(),
                            ComparisonDataType.COMPARE_WITH_YOURSELF
                        )
                    )
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.compareWithYourself.isVisible =
            arguments?.getBoolean(ARG_PROFILE_RESULT_TYPE) ?: false
        setListeners()

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            binding.tabMenu.isVisible = true
            it.let(::initAdapter)
        }

        arguments?.let {
            val profileResultType = it.getSerializable(PROFILE_RESULT_TYPE) as ProfileResultType
            binding.compareWithYourself.isVisible = profileResultType != ProfileResultType.NEW
            binding.addedToComparison.isVisible = profileResultType != ProfileResultType.NEW
        }

        loadData()
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun setListeners() {
        binding.addedProfile.setOnClickListener {
            viewModel.playerModel.get()?.let {
                if (viewModel.playerModel.get()?.stats?.playerStatsData?.stats?.all?.overall?.matches == 0) {
                    context?.showDialog(R.string.user_empty_stats)
                } else {
                    registrationProfileCallback?.addedProfile(it)
                    dismiss()
                }
            }
        }

        binding.compareWithYourself.setOnClickListener {
            if (viewModel.playerModel.get()?.stats?.playerStatsData?.stats?.all?.overall?.matches == 0) {
                //  context?.showDialog(R.string.steam_private) TODO
            } else {
                viewModel.compareWithYourself()
            }
        }

        binding.addedToComparison.setOnClickListener {
            dismiss()
            viewModel.addedUserMode()
        }

        setErrorResolveButtonClick {
            loadData()
        }
    }

    private fun loadData() {
        arguments?.let {
            viewModel.loadData(
                it.getString(ACCOUNT_ID, ""),
                it.getString(IMAGE_URL, "")
            )
        }
    }

    private fun initAdapter(list: List<ProfileListItem>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = ProfileAdapter()
            (adapter as ProfileAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "ProfileResultFragment"
        private const val PROFILE_RESULT_TYPE = "profile_result_type"
        private const val IMAGE_URL = "img_url"

        fun show(
            fragmentManager: FragmentManager?,
            accountId: String,
            imageUrl: String,
            profileResultType: ProfileResultType,
            registrationProfileCallback: RegistrationProfileCallback
        ) {
            val profileResultFragment = ProfileResultFragment().apply {
                this.registrationProfileCallback = registrationProfileCallback
                arguments = Bundle().apply {
                    putString(ACCOUNT_ID, accountId)
                    putString(IMAGE_URL, imageUrl)
                    putSerializable(PROFILE_RESULT_TYPE, profileResultType)
                }
            }
            fragmentManager?.let {
                profileResultFragment.show(
                    it,
                    TAG
                )
            }
        }
    }
}
