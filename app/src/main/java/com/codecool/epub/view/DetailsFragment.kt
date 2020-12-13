package com.codecool.epub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.FragmentDetailsBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import org.koin.android.ext.android.inject

class DetailsFragment : Fragment() {

    private val requestManager: RequestManager by inject()
    private val args: DetailsFragmentArgs by navArgs()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply { this.duration = duration }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply { this.duration = duration }
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
        binding.detailsAppBar.toolbarIcon.visibility = View.GONE
        binding.detailsAppBar.searchIcon.setOnClickListener { navigateToSearchFragment(it) }
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.detailsAppBar.toolBar.setupWithNavController(navController, appBarConfiguration)
        val game = args.game
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