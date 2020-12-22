package com.codecool.epub.view.ui

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
import androidx.core.content.ContextCompat.getColor
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.view.adapter.CategoryAdapter
import com.codecool.epub.view.adapter.RecommendedStreamAdapter
import com.codecool.epub.databinding.FragmentHomeBinding
import com.codecool.epub.databinding.MainAppBarBinding
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.RecommendationData
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.adapter.CategoryAdapterListener
import com.codecool.epub.view.adapter.StreamAdapterListener
import com.codecool.epub.viewmodel.HomeViewModel
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class HomeFragment : Fragment(), CategoryAdapterListener, StreamAdapterListener {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private val requestManager: RequestManager by inject()
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarBinding: MainAppBarBinding

    // Adapters
    private val topStreamsAdapter = RecommendedStreamAdapter(requestManager, this)
    private val categoryAdapter = CategoryAdapter(requestManager,this)
    private val recommendedStreamsAdapter1 = RecommendedStreamAdapter(requestManager, this)
    private val recommendedStreamsAdapter2 = RecommendedStreamAdapter(requestManager, this)

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
        appBarBinding = MainAppBarBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        appBarBinding.searchIcon.setOnClickListener { navigateToSearchFragment(it) }
        recyclerViewsInit()
        viewModel.getRecommendationData().observe(viewLifecycleOwner, {
            binding.homePageLoading.visibility = View.GONE
            when (it) {
                is RecommendationData.OnSuccess -> displayRecommendations(it)
                is RecommendationData.OnError -> displayError(it.exception)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun recyclerViewsInit() {
        binding.topStreamsRecyclerView.adapter = topStreamsAdapter
        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.recommendedStreamsRecyclerView1.adapter = recommendedStreamsAdapter1
        binding.recommendedStreamsRecyclerView2.adapter = recommendedStreamsAdapter2
    }

    private fun displayRecommendations(recommendation: RecommendationData.OnSuccess) {
        topStreamsAdapter.submitList(recommendation.topStreamsResponse.data)

        binding.categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))
        categoryAdapter.submitList(recommendation.topCategories.data)

        val recommendedStreamsTitleStart = getString(R.string.recommended_streams_title_1)
        val recommendedStreamsTitleEnd = getString(R.string.recommended_streams_title_2)

        val recommendedCategoryName1 = recommendation.recommendedStreams1.data.first().categoryName
        val recommendedStreamsTitle1 = "$recommendedStreamsTitleStart $recommendedCategoryName1 $recommendedStreamsTitleEnd"
        binding.recommendedStreamsTitle1.text = highlightText(recommendedStreamsTitle1, recommendedCategoryName1)
        recommendedStreamsAdapter1.submitList(recommendation.recommendedStreams1.data)

        val recommendedCategoryName2 = recommendation.recommendedStreams2.data.first().categoryName
        val recommendedStreamsTitle2 = "$recommendedStreamsTitleStart $recommendedCategoryName2 $recommendedStreamsTitleEnd"
        binding.recommendedStreamsTitle2.text = highlightText(recommendedStreamsTitle2, recommendedCategoryName2)
        recommendedStreamsAdapter2.submitList(recommendation.recommendedStreams2.data)
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

    override fun onCategoryClicked(category: CategoryResponse.Category) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(category)
        val appBarTransitionName = getString(R.string.app_bar_transition_name)
        val extras = FragmentNavigatorExtras(appBarBinding.appBar to appBarTransitionName)
        findNavController().navigate(action, extras)
    }

    override fun onStreamClicked(stream: StreamsResponse.Stream) {
        Log.d(TAG, "onStreamClicked: ${stream.title}")
    }
}