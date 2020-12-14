package com.codecool.epub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.adapter.StreamsAdapter
import com.codecool.epub.databinding.FragmentDetailsBinding
import com.codecool.epub.model.GamesResponse
import com.codecool.epub.viewmodel.HomeViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val requestManager: RequestManager by inject()
    private val args: DetailsFragmentArgs by navArgs()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
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
        binding.detailsAppBar.searchIcon.setOnClickListener { navigateToSearchFragment(it) }
        setupCategory()
    }

    private fun setupCategory() {
        val game = requireArguments().get("game") as GamesResponse.Game
        val adapter = StreamsAdapter(requestManager)
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.categoriesRecyclerView.adapter = adapter
        viewModel.getVideos().observe(viewLifecycleOwner, {
            adapter.submitList(it.data)
        })
        viewModel.fetchVideos(game.id)
        requestManager.load(game.getImageUrl(150, 200)).into(binding.categoryImage)
        binding.categoryName.text = game.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToSearchFragment(view: View) {
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
        val action = DetailsFragmentDirections.actionDetailsFragmentToSearchFragment()
        val searchButtonTransitionName = getString(R.string.search_button_transition_name)
        val extras = FragmentNavigatorExtras(view to searchButtonTransitionName)
        findNavController().navigate(action, extras)
    }
}