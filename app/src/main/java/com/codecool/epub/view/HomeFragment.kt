package com.codecool.epub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val requestManager: RequestManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // TEST DATA
        val testData = listOf(GameResponse.Game("493057", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493058", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
            GameResponse.Game("493059", "PLAYER UNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"))

        val testAdapter = CategoryAdapter(requestManager, testData)
        categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = testAdapter
        }
        testAdapter.onItemClick = {

            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)

            //val extras = FragmentNavigatorExtras(image to "image_transition_two")
            findNavController().navigate(action)
        }
    }
}