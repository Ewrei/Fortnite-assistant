package robin.vitalij.fortniteassitant.ui.news.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.interfaces.AdapterNewsCallback
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.NewsType
import robin.vitalij.fortniteassitant.model.network.NewsModel
import robin.vitalij.fortniteassitant.ui.news.fragment.adapter.NewsAdapter
import javax.inject.Inject


class NewsFragment : Fragment(R.layout.fragment_recycler_view) {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewBinding::bind)

    private var adapterNewsCallback: AdapterNewsCallback? = null

    private val newsAdapter = NewsAdapter(
        onVideoClick = { videoUrl: String, videoName: String ->
            adapterNewsCallback?.onVideoClick(videoUrl, videoName)
        }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.newsType = it.getSerializable(ARG_NEWS_TYPE) as NewsType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        binding.viewEmptyInclude.empty.setText(R.string.no_information)

        lifecycleScope.launch {
            viewModel.newsResult.collect {
                handleNewsResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListener() {
        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleNewsResult(result: LoadingState<List<NewsModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.viewErrorInclude.errorView.isVisible = false
            }

            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                newsAdapter.submitList(result.data)
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()
            }

            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.viewErrorInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        private const val ARG_NEWS_TYPE = "arg_news_type"

        fun newInstance(newsType: NewsType, adapterNewsCallback: AdapterNewsCallback) =
            NewsFragment().apply {
                this.adapterNewsCallback = adapterNewsCallback
                arguments = bundleOf(ARG_NEWS_TYPE to newsType)
            }
    }

}