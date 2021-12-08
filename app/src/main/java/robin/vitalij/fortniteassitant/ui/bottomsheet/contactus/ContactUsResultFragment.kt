package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_recyclerview.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.intentGmail
import robin.vitalij.fortniteassitant.common.extensions.intentUrl
import robin.vitalij.fortniteassitant.common.extensions.intentVk
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter.ContactUsAdapter
import robin.vitalij.fortniteassitant.ui.web.WebActivity
import javax.inject.Inject

class ContactUsResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ContactUsResultViewModelFactory

    private lateinit var viewModel: ContactUsResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.bottom_sheet_recyclerview, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ContactUsResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })

        arguments?.let {
            viewModel.loadData(it.getBoolean(ARG_IS_CONTACT_US))
        }
    }

    private fun initAdapter(list: List<ContactUsModel>) {
        recyclerView.run {
            adapter = ContactUsAdapter {
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
            (adapter as ContactUsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "ContactUsResultFragment"
        private const val ARG_IS_CONTACT_US = "arg_is_contact_us"

        fun show(fragmentManager: FragmentManager?, isContactUs: Boolean) {
            fragmentManager?.let {
                ContactUsResultFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(ARG_IS_CONTACT_US, isContactUs)
                    }
                }
                    .show(
                        it,
                        TAG
                    )
            }
        }
    }
}
