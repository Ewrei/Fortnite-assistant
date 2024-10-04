package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.intentGmail
import robin.vitalij.fortniteassitant.common.extensions.intentUrl
import robin.vitalij.fortniteassitant.common.extensions.intentVk
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter.ContactUsAdapter
import robin.vitalij.fortniteassitant.ui.web.WebActivity
import javax.inject.Inject

class ContactUsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ContactUsResultViewModelFactory

    private val viewModel: ContactUsResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetMvvmBinding? = null

    private val binding get() = _binding!!

    private val contactUsAdapter = ContactUsAdapter {
        handleContactUs(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMvvmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.isContactUs = it.getBoolean(ARG_IS_CONTACT_US)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        setListener()

        lifecycleScope.launch {
            viewModel.contactUsResult.collect {
                handleContactUsResult(it)
            }
        }

        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = contactUsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleContactUsResult(result: LoadingState<List<ContactUsModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                contactUsAdapter.submitList(result.data)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun handleContactUs(contactUsModel: ContactUsModel) {
        when (contactUsModel.configType) {
            ConfigType.GMAIL -> {
                activity?.intentGmail(contactUsModel.url)
            }
            ConfigType.VK -> {
                activity?.intentVk(contactUsModel.url)
            }
            ConfigType.FOUND_ACCOUNT_ID_IN_EPIC_GAMES, ConfigType.FOUND_ACCOUNT_ID_IN_FORTNITE -> {
                startActivity(
                    WebActivity.newInstance(
                        context,
                        contactUsModel.url,
                        getString(contactUsModel.configType.getNameRes())
                    )
                )
            }
            else -> {
                activity?.intentUrl(contactUsModel.url)
            }
        }
    }

    companion object {
        private const val TAG = "ContactUsResultFragment"
        private const val ARG_IS_CONTACT_US = "arg_is_contact_us"

        fun show(fragmentManager: FragmentManager, isContactUs: Boolean) {
            ContactUsResultFragment().apply {
                arguments = bundleOf(ARG_IS_CONTACT_US to isContactUs)
            }.show(fragmentManager, TAG)
        }
    }
}
