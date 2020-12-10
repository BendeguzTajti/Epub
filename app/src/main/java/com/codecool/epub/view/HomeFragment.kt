package com.codecool.epub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.adapter.CategoryAdapter
import com.codecool.epub.model.GameResponse
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.CategoryAdapterListener{

    private val requestManager: RequestManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


        // TEST DATA
        val testData = listOf(GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493058", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493059", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"))

        val testAdapter = CategoryAdapter(requestManager, testData, this)
        categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = testAdapter
        }
    }

    override fun onCategoryClicked(card: CardView, game: GameResponse.Game) {
        exitTransition = Hold()
        reenterTransition = Hold()
        val extras = FragmentNavigatorExtras(card to "image_transition")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(game)
        findNavController().navigate(action, extras)
    }
}