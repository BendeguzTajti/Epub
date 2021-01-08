package com.codecool.epub.view.ui

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.codecool.epub.R
import com.codecool.epub.view.adapter.CategoryStreamAdapter
import com.codecool.epub.databinding.FragmentDetailsBinding
import com.codecool.epub.di.GlideApp
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.adapter.CategoryStreamLoadStateAdapter
import com.codecool.epub.viewmodel.DetailsViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    companion object {
        private const val TAG = "DetailsFragment"
        private const val SPAN_COUNT_PORTRAIT = 1
        private const val SPAN_COUNT_LANDSCAPE = 2
        private const val MAX_ITEM_PRELOAD = 8
    }

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryStreamAdapter: CategoryStreamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            excludeTarget(R.id.details_app_bar, true)
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            excludeTarget(R.id.details_app_bar, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.detailsToolBar.setupWithNavController(navController, appBarConfiguration)
        adapterInit()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCategoryStreams(args.categoryId).collectLatest {
                categoryStreamAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        binding.categoryStreamsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun adapterInit() {
        val placeholderColor = ContextCompat.getColor(requireContext(), R.color.placeholder_color)
        val requestManager = GlideApp.with(requireContext())
        val thumbnailWidth = getThumbnailWidth()
        val thumbnailHeight = getThumbnailHeight()
        val thumbnailLoader = requestManager.asDrawable()
            .override(thumbnailWidth, thumbnailHeight)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(ColorDrawable(placeholderColor))

        categoryStreamAdapter = CategoryStreamAdapter(thumbnailLoader) {
            it?.let { onStreamClicked(it) }
        }
        addThumbnailPreLoader(FixedPreloadSizeProvider(thumbnailWidth, thumbnailHeight))
        binding.categoryStreamsRecyclerView.apply {
            layoutManager = getCategoryLayoutManager()
            adapter = categoryStreamAdapter.withLoadStateFooter(CategoryStreamLoadStateAdapter())
            setRecyclerListener { holder ->
                val thumbnailImageView = holder.itemView.findViewById<ImageView>(R.id.category_stream_thumbnail)
                thumbnailImageView?.let { requestManager.clear(it) }
            }
        }
        categoryStreamAdapter.addLoadStateListener { loadState ->
            binding.categoryStreamsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.detailsPageLoading.isVisible = loadState.source.refresh is LoadState.Loading
            // TODO : Display error when the list is empty
//            binding.errorContainer.isVisible = loadState.source.refresh is LoadState.Error
//            val errorState = loadState.source.append as? LoadState.Error
//                ?: loadState.source.prepend as? LoadState.Error
//                ?: loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//            val error = errorState.error
        }
    }

    private fun addThumbnailPreLoader(preLoadSizeProvider: FixedPreloadSizeProvider<StreamsResponse.Stream>) {
        val preLoader = RecyclerViewPreloader(
            GlideApp.with(requireContext()), categoryStreamAdapter, preLoadSizeProvider, MAX_ITEM_PRELOAD
        )
        binding.categoryStreamsRecyclerView.apply {
            addOnScrollListener(preLoader)
            setItemViewCacheSize(0)
        }
    }

    private fun getCategoryLayoutManager(): GridLayoutManager {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(requireContext(), SPAN_COUNT_LANDSCAPE).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (categoryStreamAdapter.getItemViewType(position) == R.layout.category_stream_item) 1 else 2
                    }
                }
            }
        } else {
            GridLayoutManager(requireContext(), SPAN_COUNT_PORTRAIT)
        }
    }

    private fun onStreamClicked(stream: StreamsResponse.Stream) {
        val intent = Intent(activity, VideoActivity::class.java)
        intent.putExtra(VideoActivity.CHANNEL_NAME_KEY, stream.getChannelName())
        startActivity(intent)
    }

    private fun getThumbnailWidth(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels
        val recyclerViewPaddingPx = resources.getDimensionPixelSize(R.dimen.recycler_view_padding_side)
        val cardMarginPx = resources.getDimensionPixelSize(R.dimen.card_margin)
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val columnCount = 2
            screenWidthPx / columnCount - recyclerViewPaddingPx * 2 - cardMarginPx
        } else {
            screenWidthPx - recyclerViewPaddingPx * 2 - cardMarginPx * 2
        }
    }

    private fun getThumbnailHeight(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            resources.getDimensionPixelSize(R.dimen.category_stream_thumbnail_height_landscape)
        } else {
            resources.getDimensionPixelSize(R.dimen.category_stream_thumbnail_height_portrait)
        }
    }
}