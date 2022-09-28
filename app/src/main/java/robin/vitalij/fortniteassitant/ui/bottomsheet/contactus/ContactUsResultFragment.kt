package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.intentGmail
import robin.vitalij.fortniteassitant.common.extensions.intentUrl
import robin.vitalij.fortniteassitant.common.extensions.intentVk
import robin.vitalij.fortniteassitant.databinding.BottomSheetRecyclerviewBinding
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter.ContactUsAdapter
import robin.vitalij.fortniteassitant.ui.web.WebActivity
import javax.inject.Inject

class ContactUsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ContactUsResultViewModelFactory

    private lateinit var viewModel: ContactUsResultViewModel

    private lateinit var binding: BottomSheetRecyclerviewBinding

    private val contactUsAdapter = ContactUsAdapter {
        when (it.configType) {
            ConfigType.GMAIL -> {
                activity?.intentGmail(it.url)
            }
            ConfigType.VK -> {
                activity?.intentVk(it.url)
            }
            ConfigType.FOUND_ACCOUNT_ID_IN_EPIC_GAMES, ConfigType.FOUND_ACCOUNT_ID_IN_FORTNITE -> {
                startActivity(
                    WebActivity.newInstance(
                        context,
                        it.url,
                        getString(it.configType.getNameRes())
                    )
                )
            }
            else -> {
                activity?.intentUrl(it.url)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetRecyclerviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ContactUsResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.loadData(it.getBoolean(ARG_IS_CONTACT_US))
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            contactUsAdapter.updateData(it)
        }

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.run {
            adapter = contactUsAdapter
            layoutManager = LinearLayoutManager(context)
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
