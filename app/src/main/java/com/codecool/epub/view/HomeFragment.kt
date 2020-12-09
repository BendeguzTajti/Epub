package com.codecool.epub.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.adapter.CategoryAdapter
import com.codecool.epub.databinding.FragmentHomeBinding
import com.codecool.epub.model.GameResponse
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val requestManager: RequestManager by inject()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


        // TEST DATA
        val testData = listOf(GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"))




        val testAdapter = CategoryAdapter(requestManager, testData)
        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = testAdapter
        }

        binding.searchIcon.setOnClickListener {
            exitTransition = MaterialFadeThrough()
            reenterTransition = MaterialFadeThrough()
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            val searchButtonTransitionName = getString(R.string.search_button_transition_name)
            val extras = FragmentNavigatorExtras(it to searchButtonTransitionName)
            findNavController().navigate(action, extras)
        }
    }

    private fun highlightText(string: String, subString: String): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(string)
        val highlightColor = ForegroundColorSpan(getColor(requireContext(), R.color.purple_500))
        val startIndex = string.indexOf(subString, 0)
        val endIndex = subString.length
        spannableString.setSpan(highlightColor, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}