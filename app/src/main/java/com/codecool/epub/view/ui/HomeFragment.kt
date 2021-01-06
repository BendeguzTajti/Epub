package com.codecool.epub.view.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import com.codecool.epub.R
import com.codecool.epub.view.adapter.RecommendedStreamAdapter
import com.codecool.epub.databinding.FragmentHomeBinding
import com.codecool.epub.di.GlideApp
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.RecommendationData
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.adapter.CategoryAdapter
import com.codecool.epub.viewmodel.HomeViewModel
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Adapters
    private lateinit var topStreamsAdapter: RecommendedStreamAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recommendedStreamsAdapter1: RecommendedStreamAdapter
    private lateinit var recommendedStreamsAdapter2: RecommendedStreamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.searchIcon.setOnClickListener { navigateToSearchFragment(it) }
        binding.homePageSwipeRefresh.isEnabled = false
        binding.homePageSwipeRefresh.setOnRefreshListener { viewModel.refreshData() }
        recyclerViewsInit()
        viewModel.recommendationData().observe(viewLifecycleOwner, {
            binding.homePageLoading.visibility = View.GONE
            binding.homePageSwipeRefresh.isEnabled = true
            when (it) {
                is RecommendationData.OnSuccess -> displayRecommendations(it)
                is RecommendationData.OnError -> displayError(it.exception)
            }
        })
        viewModel.isRefreshing().observe(viewLifecycleOwner, {
            binding.homePageSwipeRefresh.isRefreshing = it
        })
    }

    override fun onDestroyView() {
        destroyAdapters()
        _binding = null
        super.onDestroyView()
    }

    private fun recyclerViewsInit() {
        val placeholderColor = getColor(requireContext(), R.color.placeholder_color)
        val thumbnailWidth = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_width)
        val thumbnailHeight = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_height)
        val requestManager = GlideApp.with(requireContext())
        val thumbnailLoader = requestManager.asDrawable()
            .override(thumbnailWidth, thumbnailHeight)
            .placeholder(ColorDrawable(placeholderColor))

        topStreamsAdapter = RecommendedStreamAdapter(thumbnailLoader) { onStreamClicked(it) }
        categoryAdapter = CategoryAdapter { onCategoryClicked(it) }
        recommendedStreamsAdapter1 = RecommendedStreamAdapter(thumbnailLoader) { onStreamClicked(it) }
        recommendedStreamsAdapter2 = RecommendedStreamAdapter(thumbnailLoader) { onStreamClicked(it) }

        binding.topStreamsRecyclerView.apply {
            adapter = topStreamsAdapter
            setRecyclerListener {
                requestManager.clear(it.itemView.findViewById<ImageView>(R.id.recommended_stream_thumbnail))
            }
        }
        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.recommendedStreamsRecyclerView1.apply {
            adapter = recommendedStreamsAdapter1
            setRecyclerListener {
                requestManager.clear(it.itemView.findViewById<ImageView>(R.id.recommended_stream_thumbnail))
            }
        }
        binding.recommendedStreamsRecyclerView2.apply {
            adapter = recommendedStreamsAdapter2
            setRecyclerListener {
                requestManager.clear(it.itemView.findViewById<ImageView>(R.id.recommended_stream_thumbnail))
            }
        }
    }

    private fun displayRecommendations(recommendation: RecommendationData.OnSuccess) {
        topStreamsAdapter.submitList(recommendation.topStreams)
        categoryAdapter.submitList(recommendation.topCategories)
        recommendedStreamsAdapter1.submitList(recommendation.recommendedStreams1)
        recommendedStreamsAdapter2.submitList(recommendation.recommendedStreams2)

        binding.categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))
        val recommendedStreamsTitleStart = getString(R.string.recommended_streams_title_1)
        val recommendedStreamsTitleEnd = getString(R.string.recommended_streams_title_2)

        val recommendedCategoryName1 = recommendation.recommendedStreams1.first().categoryName
        val recommendedStreamsTitle1 = "$recommendedStreamsTitleStart $recommendedCategoryName1 $recommendedStreamsTitleEnd"
        binding.recommendedStreamsTitle1.text = highlightText(recommendedStreamsTitle1, recommendedCategoryName1)

        val recommendedCategoryName2 = recommendation.recommendedStreams2.first().categoryName
        val recommendedStreamsTitle2 = "$recommendedStreamsTitleStart $recommendedCategoryName2 $recommendedStreamsTitleEnd"
        binding.recommendedStreamsTitle2.text = highlightText(recommendedStreamsTitle2, recommendedCategoryName2)
        binding.homePageContent.visibility = View.VISIBLE
    }

    private fun displayError(exception: Exception) {
        Log.d(TAG, "displayError: $exception")
    }

    private fun highlightText(string: String, subString: String): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(string)
        val highlightColor = ForegroundColorSpan(getColor(requireContext(), R.color.purple_500))
        val startIndex = string.indexOf(subString, 0)
        val endIndex = startIndex + subString.length
        spannableString.setSpan(highlightColor, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    private fun destroyAdapters() {
        binding.topStreamsRecyclerView.adapter = null
        binding.categoryRecyclerView.adapter = null
        binding.recommendedStreamsRecyclerView1.adapter = null
        binding.recommendedStreamsRecyclerView2.adapter = null
    }

    private fun navigateToSearchFragment(view: View) {
        exitTransition = MaterialFade().apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
        reenterTransition = MaterialFade().apply {
            duration = resources.getInteger(R.integer.reenter_motion_duration_small).toLong()
        }
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        val searchButtonTransitionName = getString(R.string.search_button_transition_name)
        val extras = FragmentNavigatorExtras(view to searchButtonTransitionName)
        findNavController().navigate(action, extras)
    }

    private fun onCategoryClicked(category: CategoryResponse.Category) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            excludeTarget(R.id.home_app_bar, true)
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            excludeTarget(R.id.home_app_bar, true)
        }
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(category.id, category.name)
        findNavController().navigate(action)
    }

    private fun onStreamClicked(stream: StreamsResponse.Stream) {
        val intent = Intent(activity, VideoActivity::class.java)
        intent.putExtra(VideoActivity.CHANNEL_NAME_KEY, stream.getChannelName())
        startActivity(intent)
    }
}