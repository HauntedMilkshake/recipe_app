package com.example.recipe_app.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.R
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.databinding.FragmentRecipesSearchBinding

class FragmentRecipesSearch: Fragment() {
    private var _binding: FragmentRecipesSearchBinding? = null

    private val binding get() = _binding!!
    private val searchViewModel: FragmentRecipesSearchViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())


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
                    //submit is whether to search immediately after youve autofilled or keep typing and wait for enter to be pressed
                    binding.recipeSearchBar.setQuery(item.title, false)
                }
            }
        }
        val searchAdapter = SearchAdapter().apply {
            itemClickListener = object : ItemClickListener<BareRecipe> {
                override fun onItemClicked(item: BareRecipe, itemPosition: Int) {
                    findNavController().navigate(R.id.search_to_recipe_information, bundleOf("recipe_id" to item.id))
                    //go to fragment with recipe information and send the id as an argument
                }
            }
        }

        binding.apply {
            autocompleteRecyclerView.apply{
                layoutManager = LinearLayoutManager(requireContext())
                //this.addItemDecoration(SpacingItemDecoration(2))
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
                            searchViewModel.fetchRecipes(query)
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
        }
        searchViewModel.autoCompleteText.observe(viewLifecycleOwner){
            Log.d("AutoCompleteData", it.toString())
            binding.apply { autocompleteAdapter.updateItems(it) }
        }
        searchViewModel.recipes.observe(viewLifecycleOwner){
            binding.apply { searchAdapter.updateItems(it) }
        }
    }
}
//class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
//    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        outRect.bottom = spacing
//    }
//}



