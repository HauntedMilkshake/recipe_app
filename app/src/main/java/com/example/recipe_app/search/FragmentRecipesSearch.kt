package com.example.recipe_app.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.R
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.databinding.FragmentRecipesSearchBinding
import com.example.recipe_app.search.mode.SearchMode
import com.google.android.material.button.MaterialButton

class FragmentRecipesSearch: Fragment() {
    private var _binding: FragmentRecipesSearchBinding? = null

    private val binding get() = _binding!!
    private val searchViewModel: FragmentRecipesSearchViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private var popupWindow: PopupWindow? = null
    private  var searchMode: SearchMode = SearchMode.COMPLEXSEARCH



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val autocompleteAdapter = AutoCompleteAdapter().apply {
            itemClickListener = object : ItemClickListener<AutoCompleteResult> {
                override fun onItemClicked(item: AutoCompleteResult, itemPosition: Int) {
                    Log.d("CLICKED", "WOW")
                    //submit is whether to search immediately after you've auto filled or keep typing and wait for enter to be pressed
                    binding.recipeSearchBar.setQuery(item.title, false)
                }
            }
        }
        val searchAdapter = SearchAdapter().apply {
            itemClickListener = object : ItemClickListener<Recipe> {
                override fun onItemClicked(item: Recipe, itemPosition: Int) {
                    findNavController().navigate(R.id.search_to_recipe_information, bundleOf("recipe_id" to item.id))
                }
            }
        }

        binding.apply {
            autocompleteRecyclerView.apply{
                layoutManager = LinearLayoutManager(requireContext())
                adapter = autocompleteAdapter
            }
            searchRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = searchAdapter

            }
            recipeSearchBar.let {
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let{
                            searchViewModel.fetchRecipes(query, searchMode)
                        }
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        query?.let {
                            handler.removeCallbacksAndMessages(null)
                            handler.postDelayed({
                                searchViewModel.fetchAutoCompleteText(query)
                            }, 1000)
                        }
                        return true
                    }
                })
            }
            settingsButton.setOnClickListener {
                showPopUp(it, R.layout.search_filter_pop_up)
            }
        }
        searchViewModel.autoCompleteText.observe(viewLifecycleOwner){
            binding.apply { autocompleteAdapter.updateItems(it) }
        }
        searchViewModel.recipes.observe(viewLifecycleOwner){
            binding.apply { searchAdapter.updateItems(it) }
        }
    }
    private fun showPopUp(anchorView: View, popUpId: Int) {

        val popUpView = layoutInflater.inflate(popUpId, null)
        val closeButton = popUpView.findViewById<ImageView>(R.id.settings_exit_button)
        val radioGroup = popUpView.findViewById<RadioGroup>(R.id.filters_radio_group)
        val resetFilterButton = popUpView.findViewById<MaterialButton>(R.id.reset_button)

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            val ingredientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_ingredients_button)
            val nutrientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_nutrients_button)

            if (ingredientFilterButton.isChecked) {
                searchMode = SearchMode.SEARCHBYINGREDIENTS
            } else if (nutrientFilterButton.isChecked) {
                searchMode = SearchMode.SEARCHBYNUTRIENTS
            } else {
                searchMode = SearchMode.COMPLEXSEARCH
            }

            resetFilterButton.setOnClickListener {
                ingredientFilterButton.isChecked = false
                nutrientFilterButton.isChecked = false
            }
        }

        popupWindow = PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        closeButton.setOnClickListener {
            popupWindow?.dismiss()
        }

        popupWindow?.showAsDropDown(anchorView)
    }

}




