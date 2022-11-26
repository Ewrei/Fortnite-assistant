package robin.vitalij.fortniteassitant.ui.bottomsheet.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_user.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.showApplicationDialog
import robin.vitalij.fortniteassitant.common.extensions.showToast
import robin.vitalij.fortniteassitant.databinding.BottomSheetUserBinding
import robin.vitalij.fortniteassitant.interfaces.UsersCallback
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity
import javax.inject.Inject

class UserResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: UserResultViewModelFactory

    private lateinit var viewModel: UserResultViewModel

    private var usersCallback: UsersCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding =
            DataBindingUtil.inflate<BottomSheetUserBinding>(
                inflater,
                R.layout.bottom_sheet_user,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@UserResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(UserResultViewModel::class.java).apply {
                openCompare = {
                    dismiss()
                    startActivity(
                        ComparisonActivity.getComparisonActivityIntent(
                            requireContext(),
                            ComparisonDataType.COMPARE_WITH_YOURSELF
                        )
                    )
                }

                showRemoveUserToast = {
                    context?.showToast(getString(R.string.delete_account_message))
                    dismiss()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.setPlayerId(it.getString(ARG_ACCOUNT_ID, ""))
        }

        setListeners()
    }

    private fun setListeners() {
        updateButton.setOnClickListener {
            dismiss()
            usersCallback?.updateProfile(viewModel.currentId)
        }

        switchButton.setOnClickListener {
            dismiss()
            usersCallback?.switch(viewModel.currentId)
        }

        compareWithYourself.setOnClickListener {
            viewModel.compareWithYourself()
        }

        removeAccount.setOnClickListener {
            context?.showApplicationDialog(
                getString(
                    R.string.delete_account_title, arguments?.getString(
                        ARG_PLAYER_NAME
                    )
                ),
                onPositiveClickListener = { _, _ ->
                    viewModel.deleteProfile(
                        viewModel.currentId
                    )
                }
            )
        }
    }

    companion object {
        private const val ARG_ACCOUNT_ID = "arg_account_id"
        private const val ARG_PLAYER_NAME = "arg_player_name"

        private const val TAG = "UserResultFragment"

        fun show(
            fragmentManager: FragmentManager,
            playerId: String,
            playerName: String,
            usersCallback: UsersCallback
        ) {
            UserResultFragment().apply {
                this.usersCallback = usersCallback
                arguments = bundleOf(
                    ARG_ACCOUNT_ID to playerId,
                    ARG_PLAYER_NAME to playerName
                )
            }.show(fragmentManager, TAG)
        }
    }
}
