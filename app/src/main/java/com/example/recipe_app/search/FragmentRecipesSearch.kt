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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.R
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.databinding.FragmentRecipesSearchBinding
import com.example.recipe_app.search.mode.SearchMode
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider

class FragmentRecipesSearch: Fragment() {
    private var _binding: FragmentRecipesSearchBinding? = null

    private val binding get() = _binding!!
    private val searchViewModel: FragmentRecipesSearchViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private var popupWindow: PopupWindow? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recipeSearchBar.setQuery(arguments?.getString("autofill"), true)
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
            itemClickListener = object : SearchAdapter.ItemClickListener<Recipe> {
                override fun onItemClicked(item: Recipe, itemPosition: Int, clickedView: View) {
                    when(clickedView.id){
                        R.id.recipe_image -> findNavController().navigate(R.id.search_to_recipe_information, bundleOf("recipe_id" to item.id))
//                        R.id.favourite_image -> searchViewModel.favouriteRecipe(item.id)
                    }
                }
            }
        }

        binding.apply {
            autocompleteRecyclerView.apply{
                layoutManager = LinearLayoutManager(requireContext())
                adapter = autocompleteAdapter
            }
            searchRecyclerView.apply {
//                layoutManager = GridLayoutManager(context, 2)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = searchAdapter

            }
            recipeSearchBar.let {
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let{
                            searchViewModel.fetchRecipes(query, 10, 100, 10, 100, 10, 100)
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
    //I am not writing a cleaner version we are far beyond that
    //bug: if u check a radio button, then reset and then check nutrients it doesn't get checked until you check the other one and then check nutrients

    private fun showPopUp(anchorView: View, popUpId: Int) {
        var popUpSearchMode = searchViewModel.getSearchMode()
        val popUpView = layoutInflater.inflate(popUpId, null)
        val closeButton = popUpView.findViewById<ImageView>(R.id.settings_exit_button)
        val saveButton = popUpView.findViewById<MaterialButton>(R.id.save_current_settings)
        val radioGroup = popUpView.findViewById<RadioGroup>(R.id.filters_radio_group)
        val resetFilterButton = popUpView.findViewById<MaterialButton>(R.id.reset_button)
        val carbsRangeSlider = popUpView.findViewById<RangeSlider>(R.id.carb_range_slider)
        val proteinRangeSlider = popUpView.findViewById<RangeSlider>(R.id.protein_range_slider)
        val fatRangeSlider = popUpView.findViewById<RangeSlider>(R.id.fat_range_slider)

        when(searchViewModel.getSearchMode()){
            SearchMode.SEARCHBYNUTRIENTS -> {
                radioGroup.check(R.id.search_by_nutrients_button)
                carbsRangeSlider.visibility = View.VISIBLE
                proteinRangeSlider.visibility = View.VISIBLE
                fatRangeSlider.visibility = View.VISIBLE
            }
            SearchMode.SEARCHBYINGREDIENTS ->{
                radioGroup.check(R.id.search_by_ingredients_button)
                carbsRangeSlider.visibility = View.GONE
                proteinRangeSlider.visibility = View.GONE
                fatRangeSlider.visibility = View.GONE
            }
            else ->{
                carbsRangeSlider.visibility = View.GONE
                proteinRangeSlider.visibility = View.GONE
                fatRangeSlider.visibility = View.GONE
            }
        }
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            val ingredientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_ingredients_button)
            val nutrientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_nutrients_button)

            if (nutrientFilterButton.isChecked) {
                popUpSearchMode = SearchMode.SEARCHBYNUTRIENTS
                carbsRangeSlider.visibility = View.VISIBLE
                proteinRangeSlider.visibility = View.VISIBLE
                fatRangeSlider.visibility = View.VISIBLE

            } else if (ingredientFilterButton.isChecked) {
                popUpSearchMode = SearchMode.SEARCHBYINGREDIENTS
                carbsRangeSlider.visibility = View.GONE
                proteinRangeSlider.visibility = View.GONE
                fatRangeSlider.visibility = View.GONE
            } else {
                popUpSearchMode = SearchMode.COMPLEXSEARCH
                carbsRangeSlider.visibility = View.GONE
                proteinRangeSlider.visibility = View.GONE
                fatRangeSlider.visibility = View.GONE
            }
        }

        popupWindow = PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        closeButton.setOnClickListener {
            popupWindow?.dismiss()
        }
        saveButton.setOnClickListener {
            searchViewModel.setSearchMode(popUpSearchMode)
            if(searchViewModel.getSearchMode() == SearchMode.SEARCHBYNUTRIENTS){
                searchViewModel.setCarbRange(carbsRangeSlider.values[0].toInt(), carbsRangeSlider.values[1].toInt())
                searchViewModel.setProteinRange(proteinRangeSlider.values[0].toInt(), proteinRangeSlider.values[1].toInt())
                searchViewModel.setFatRange(fatRangeSlider.values[0].toInt(), fatRangeSlider.values[1].toInt())
                searchViewModel.fetchRecipes("", searchViewModel.getCarbRange().first, searchViewModel.getCarbRange().second, searchViewModel.getProteinRange().first, searchViewModel.getProteinRange().second, searchViewModel.getFatRange().first, searchViewModel.getFatRange().second)
            }
        }
        resetFilterButton.setOnClickListener {
            popUpSearchMode = SearchMode.COMPLEXSEARCH

            val ingredientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_ingredients_button)
            val nutrientFilterButton = popUpView.findViewById<RadioButton>(R.id.search_by_nutrients_button)
            ingredientFilterButton.isChecked = false
            nutrientFilterButton.isChecked = false

            when (popUpSearchMode) {
                SearchMode.SEARCHBYNUTRIENTS -> {
                    carbsRangeSlider.visibility = View.VISIBLE
                    proteinRangeSlider.visibility = View.VISIBLE
                    fatRangeSlider.visibility = View.VISIBLE
                }
                SearchMode.SEARCHBYINGREDIENTS -> {
                    carbsRangeSlider.visibility = View.GONE
                    proteinRangeSlider.visibility = View.GONE
                    fatRangeSlider.visibility = View.GONE
                }
                else -> {
                    carbsRangeSlider.visibility = View.GONE
                    proteinRangeSlider.visibility = View.GONE
                    fatRangeSlider.visibility = View.GONE
                }
            }
        }
        popupWindow?.showAsDropDown(anchorView)
    }

}




