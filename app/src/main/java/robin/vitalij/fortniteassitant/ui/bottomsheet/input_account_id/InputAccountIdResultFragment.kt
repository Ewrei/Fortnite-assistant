package robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.afterTextChanged
import robin.vitalij.fortniteassitant.common.extensions.closeKeyboard
import robin.vitalij.fortniteassitant.common.extensions.setClickableButton
import robin.vitalij.fortniteassitant.common.extensions.setOnMultiEditorActionListener
import robin.vitalij.fortniteassitant.databinding.BottomInputAccoountIdBinding
import robin.vitalij.fortniteassitant.interfaces.InputAccountIdCallback
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import javax.inject.Inject

class InputAccountIdResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: InputAccountIdResultViewModelFactory

    private lateinit var viewModel: InputAccountIdResultViewModel

    private var accountIdCallback: InputAccountIdCallback? = null

    private lateinit var dataBinding: BottomInputAccoountIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_input_accoount_id, container, false)
        dataBinding.lifecycleOwner = this@InputAccountIdResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(InputAccountIdResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

        dataBinding.sendButton.setClickableButton(false)
    }

    override fun onResume() {
        super.onResume()
        dataBinding.inputAccountIdInputEditText.requestFocus()
    }

    private fun setListeners() {
        dataBinding.sendButton.setOnClickListener {
            accountIdCallback?.sendAccountId(dataBinding.inputAccountIdInputEditText.text.toString())
            dismiss()
        }

        dataBinding.inputAccountIdInputEditText.setOnMultiEditorActionListener(view) {
            context?.closeKeyboard(view)
            dataBinding.inputAccountIdInputEditText.clearFocus()
            dataBinding.sendButton.performClick()
        }

        dataBinding.inputAccountIdInputEditText.afterTextChanged {
            dataBinding.sendButton.setClickableButton(it.isNotEmpty())
        }

        dataBinding.howToFindAccountIdButton.setOnClickListener {
            ContactUsResultFragment.show(childFragmentManager, false)
        }
    }

    companion object {

        private const val TAG = "InputAccountIdResultFragment"

        fun show(
            fragmentManager: FragmentManager,
            inputAccountIdCallback: InputAccountIdCallback
        ) {
            InputAccountIdResultFragment().apply {
                this.accountIdCallback = inputAccountIdCallback
            }.show(fragmentManager, TAG)
        }
    }
}
