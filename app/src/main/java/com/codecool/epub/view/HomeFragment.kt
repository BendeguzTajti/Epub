package com.codecool.epub.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.adapter.CategoryAdapter
import com.codecool.epub.model.GameResponse
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val requestManager: RequestManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryTitle.text = highlightText(getString(R.string.categories_title), getString(R.string.categories_highlight_text))
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


        // TEST DATA
        val testData = listOf(GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"))




        val testAdapter = CategoryAdapter(requestManager, testData)
        categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = testAdapter
        }

        searchIcon.setOnClickListener {
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