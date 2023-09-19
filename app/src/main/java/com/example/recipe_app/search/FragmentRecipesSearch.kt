package com.example.recipe_app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.databinding.FragmentRecipesSearchBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FragmentRecipesSearch: Fragment() {
    private var _binding: FragmentRecipesSearchBinding? = null

    private val binding get() = _binding!!
    private val searchViewModel: FragmentRecipesSearchViewModel by viewModels()


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
                    //todo make the auto complete reset and send in a new api get request for example
                }
            }
        }
        binding.apply {
            autocompleteRecyclerView.adapter = autocompleteAdapter
//            recipeSearchBar.let {
//                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String?): Boolean {
//                        query?.let{
//                            searchViewModel.fetchAutoCompleteText(query)
//                            autocompleteAdapter.updateItems(searchViewModel.autoCompleteText.value!!)
//                        }
//                        return true
//                    }
//
//                    override fun onQueryTextChange(query: String?): Boolean {
//                        query?.let {
//                            searchViewModel.fetchAutoCompleteText(query)
//                            autocompleteAdapter.updateItems(searchViewModel.autoCompleteText.value!!)
//                        }
//                        return true
//                    }
//                })
//            }
            autocompleteRecyclerView.adapter = autocompleteAdapter
            recipeSearchBar.let { searchView ->
                val queryTextListener = object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            searchViewModel.fetchAutoCompleteText(it)
                            autocompleteAdapter.updateItems(searchViewModel.autoCompleteText.value!!)
                        }
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        query?.let {

                            searchViewModel.fetchAutoCompleteText(it)

                            autocompleteAdapter.updateItems(searchViewModel.autoCompleteText.value!!)
                        }
                        return true
                    }
                }
                searchView.setOnQueryTextListener(queryTextListener)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


