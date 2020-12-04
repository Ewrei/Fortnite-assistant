package robin.vitalij.fortniteassitant.ui.bottomsheet.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_recyclerview.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.intentGmail
import robin.vitalij.fortniteassitant.common.extensions.intentTelegram
import robin.vitalij.fortniteassitant.common.extensions.intentVk
import robin.vitalij.fortniteassitant.model.ContactUsModel
import robin.vitalij.fortniteassitant.model.enums.ConfigType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.adapter.ContactUsAdapter
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
    }

    private fun initAdapter(list: List<ContactUsModel>) {
        recyclerView.run {
            adapter = ContactUsAdapter {
                when (it.configType) {
                    ConfigType.GMAIL -> {
                        activity?.intentGmail(it.url)
                    }
                    ConfigType.TELEGRAM -> {
                        activity?.intentTelegram(it.url)
                    }
                    ConfigType.VK -> {
                        activity?.intentVk(it.url)
                    }
                }
            }
            (adapter as ContactUsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "ContactUsResultFragment"

        fun show(fragmentManager: FragmentManager?) {
            fragmentManager?.let {
                ContactUsResultFragment().show(
                    it,
                    TAG
                )
            }
        }
    }
}
