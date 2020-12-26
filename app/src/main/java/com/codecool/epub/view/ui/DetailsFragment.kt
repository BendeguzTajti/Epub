package com.codecool.epub.view.ui

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.view.adapter.CategoryStreamAdapter
import com.codecool.epub.databinding.FragmentDetailsBinding
import com.codecool.epub.databinding.MainAppBarBinding
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.adapter.CategoryStreamLoadStateAdapter
import com.codecool.epub.view.adapter.StreamAdapterListener
import com.codecool.epub.viewmodel.DetailsViewModel
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(), StreamAdapterListener {

    companion object {
        private const val TAG = "DetailsFragment"
        private const val SPAN_COUNT_PORTRAIT = 1
        private const val SPAN_COUNT_LANDSCAPE = 2
    }

    private val requestManager: RequestManager by inject()
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarBinding: MainAppBarBinding

    private lateinit var adapter: CategoryStreamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment
        }
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        appBarBinding = MainAppBarBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        appBarBinding.toolbarIcon.visibility = View.GONE
        appBarBinding.searchIcon.setOnClickListener { navigateToSearchFragment(it) }
        val category = args.category
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarBinding.toolBar.setupWithNavController(navController, appBarConfiguration)
        adapterInit()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCategoryStreams(category.id).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterInit() {
        this.adapter = CategoryStreamAdapter(requestManager, args.category)
        binding.categoryStreamsRecyclerView.layoutManager = getCategoryLayoutManager()
        binding.categoryStreamsRecyclerView.adapter = adapter.withLoadStateFooter(CategoryStreamLoadStateAdapter { adapter.retry() })
        adapter.addLoadStateListener { loadState ->
            binding.categoryStreamsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.detailsPageLoading.isVisible = loadState.source.refresh is LoadState.Loading
            // TODO : Display Error
//            binding.errorContainer.isVisible = loadState.source.refresh is LoadState.Error
//            val errorState = loadState.source.append as? LoadState.Error
//                ?: loadState.source.prepend as? LoadState.Error
//                ?: loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//            val error = errorState.error
        }
    }

    private fun getCategoryLayoutManager(): GridLayoutManager {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, SPAN_COUNT_LANDSCAPE)
                .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when(adapter.getItemViewType(position)) {
                            R.layout.category_stream_item -> 1
                            R.layout.category_details_header -> 2
                            else -> 1
                        }
                    }
                }
            }
        } else {
            GridLayoutManager(context, SPAN_COUNT_PORTRAIT)
        }
    }

    private fun navigateToSearchFragment(view: View) {
        exitTransition = MaterialFade().apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
        reenterTransition = MaterialFade().apply {
            duration = resources.getInteger(R.integer.reenter_motion_duration_small).toLong()
        }
        val action = DetailsFragmentDirections.actionDetailsFragmentToSearchFragment()
        val searchButtonTransitionName = getString(R.string.search_button_transition_name)
        val extras = FragmentNavigatorExtras(view to searchButtonTransitionName)
        findNavController().navigate(action, extras)
    }

    override fun onStreamClicked(stream: StreamsResponse.Stream, imageView: ImageView) {
        val intent = Intent(activity, VideoActivity::class.java)
        intent.putExtra(VideoActivity.CHANNEL_NAME_KEY, stream.getChannelName())
        startActivity(intent)
    }
}