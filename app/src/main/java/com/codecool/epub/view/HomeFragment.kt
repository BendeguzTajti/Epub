package com.codecool.epub.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
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
import com.codecool.epub.adapter.CategoryAdapter
import com.codecool.epub.adapter.StreamsAdapter
import com.codecool.epub.databinding.FragmentHomeBinding
import com.codecool.epub.databinding.MainAppBarBinding
import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.viewmodel.HomeViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), CategoryAdapter.CategoryAdapterListener {

    private val requestManager: RequestManager by inject()
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarBinding: MainAppBarBinding

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
        val categoryAdapter = CategoryAdapter(requestManager,this)
        val streamsAdapter = StreamsAdapter(requestManager)
        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.streamsRecyclerView.adapter = streamsAdapter
        viewModel.fetchTopGames()
        viewModel.getGameData().observe(viewLifecycleOwner, {
            binding.categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))
            categoryAdapter.submitList(it.data)
        })


        // TEST DATA
        val fakeStreams = StreamsResponse(listOf(StreamsResponse.Stream("26007494656", "23161357", "LIRIK", "417752", "Just Chatting","live", "Hey Guys, It's Monday - Twitter: @Lirik", 346, "https://static-cdn.jtvnw.net/previews-ttv/live_user_lirik-{width}x{height}.jpg"),
            StreamsResponse.Stream("26007494656", "23161357", "LIRIK", "417752", "Just Chatting","live", "Hey Guys, It's Monday - Twitter: @Lirik", 2117, "https://static-cdn.jtvnw.net/previews-ttv/live_user_lirik-{width}x{height}.jpg"),
            StreamsResponse.Stream("26007494656", "23161357", "LIRIK", "417752", "Just Chatting","live", "Hey Guys, It's Monday - Twitter: @Lirik", 32575, "https://static-cdn.jtvnw.net/previews-ttv/live_user_lirik-{width}x{height}.jpg")))

        streamsAdapter.submitList(fakeStreams.data)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun highlightText(string: String, subString: String): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(string)
        val highlightColor = ForegroundColorSpan(getColor(requireContext(), R.color.purple_500))
        val startIndex = string.indexOf(subString, 0)
        val endIndex = subString.length
        spannableString.setSpan(highlightColor, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    private fun navigateToSearchFragment(view: View) {
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        val searchButtonTransitionName = getString(R.string.search_button_transition_name)
        val extras = FragmentNavigatorExtras(view to searchButtonTransitionName)
        findNavController().navigate(action, extras)
    }

    override fun onCategoryClicked(game: GamesResponse.Game) {
        val duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply { this.duration = duration }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply { this.duration = duration }
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(game)
        findNavController().navigate(action)
    }
}