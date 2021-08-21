package robin.vitalij.fortniteassitant.ui.news.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.model.network.NewsModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.news.VideoActivity
import robin.vitalij.fortniteassitant.ui.news.fragment.adapter.NewsAdapter
import javax.inject.Inject


class NewsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private lateinit var viewModel: NewsViewModel

    private var _binding: FragmentRecyclerViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(NewsViewModel::class.java).apply {
                observeToProgressBar(this@NewsFragment)
                observeToError(this@NewsFragment)
                observeToEmpty(this@NewsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.loadData(it.getSerializable(NEWS_TYPE) as NewsType)
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })
    }

    private fun initAdapter(list: List<NewsModel>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = NewsAdapter(
                onVideoClick = { videoUrl: String, videoName: String ->
                    startActivity(VideoActivity.newInstance(context, videoUrl, videoName))
                }
            )
            (adapter as NewsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val NEWS_TYPE = "news_type"

        fun newInstance(newsType: NewsType) = NewsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(NEWS_TYPE, newsType)
            }
        }
    }

}