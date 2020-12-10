package com.codecool.epub.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.adapter.CategoryAdapter
import com.codecool.epub.databinding.FragmentHomeBinding
import com.codecool.epub.model.GameResponse
import com.codecool.epub.viewmodel.HomeViewModel
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), CategoryAdapter.CategoryAdapterListener {

    private val requestManager: RequestManager by inject()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

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

        homeViewModel.getGameData().observe(viewLifecycleOwner, Observer {
            homeViewModel.fetchTopGames()
        })



        binding.categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))



//        binding.categoryRecyclerView.apply {
//            adapter = CategoryAdapter(requestManager, testData, this@HomeFragment)
//            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
//        }

        binding.searchIcon.setOnClickListener {
            exitTransition = MaterialFadeThrough()
            reenterTransition = MaterialFadeThrough()
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            val searchButtonTransitionName = getString(R.string.search_button_transition_name)
            val extras = FragmentNavigatorExtras(it to searchButtonTransitionName)
            findNavController().navigate(action, extras)
        }
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

    override fun onCategoryClicked(card: CardView, game: GameResponse.Game) {
        exitTransition = Hold()
        reenterTransition = Hold()
        val extras = FragmentNavigatorExtras(card to "image_transition")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(game)
        findNavController().navigate(action, extras)
    }
}