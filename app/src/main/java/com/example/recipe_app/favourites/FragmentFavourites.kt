package com.example.recipe_app.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.R
import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.databinding.FragmentFavouritesBinding

class FragmentFavourites: Fragment() {
    private var _binding: FragmentFavouritesBinding? = null

    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favouritesAdapter = FavouritesAdapter().apply {
            itemClickListener = object : FavouritesAdapter.ItemClickListener<FavouriteRecipes> {
                override fun onItemClicked(item: FavouriteRecipes, itemPosition: Int, view: View) {
                    when(view.id){
                        R.id.recipe_image -> findNavController().navigate(R.id.favourites_to_recipe_information, bundleOf("recipe_id" to item.id))
                        R.id.favourite_image -> favouritesViewModel.removeRecipeFromRoom(item.id)
                    }
                }
            }
        }
        binding.apply {
            favouritesRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = favouritesAdapter
            }
        }
        favouritesViewModel.favouriteRecipes.observe(viewLifecycleOwner){
            favouritesAdapter.updateItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}