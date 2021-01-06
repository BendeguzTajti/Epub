package com.codecool.epub.view.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.codecool.epub.R
import com.codecool.epub.databinding.FragmentSearchBinding
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.view.adapter.CategoryAdapter
import com.codecool.epub.view.adapter.CategoryStreamAdapter
import com.codecool.epub.viewmodel.SearchViewModel
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import org.koin.android.scope.bindScope
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            scrimColor = Color.TRANSPARENT
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewInit()
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.searchToolBar.setupWithNavController(navController, appBarConfiguration)
        binding.searchView.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchViewInit() {
        binding.searchView.setOnQueryTextFocusChangeListener { view, isFocused ->
            if (isFocused) showKeyBoard(view.findFocus()) else view.clearFocus()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null) {
                    searchCategories(query)
                    searchChannels(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun onCategoryClicked(category: CategoryResponse.Category) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            excludeTarget(R.id.home_app_bar, true)
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            excludeTarget(R.id.home_app_bar, true)
        }

        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(category.id, category.name)
        findNavController().navigate(action)
    }

    private fun showKeyBoard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun searchCategories(query: String) {
        viewLifecycleOwner.lifecycleScope.launch{
            val result = viewModel.searchCategory(query)
            categoryAdapter = CategoryAdapter { onCategoryClicked(it) }
            binding.searchCategoriesTitle.text = getString(R.string.categories)
            binding.searchStreamsTitle.text = getString(R.string.streams)
            binding.searchCategoryRecyclerView.adapter = categoryAdapter
            binding.searchCategoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
            categoryAdapter.submitList(result.data)
        }
    }

    private fun searchChannels(query: String) {
        viewLifecycleOwner.lifecycleScope.launch{
            val result = viewModel.searchChannels(query)
            println("${result.data} Hello")
        }
    }
}