package com.example.recipe_app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.databinding.FragmentHomeBinding


class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: FragmentHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favouritesAdapter = FavouriteRecipesAdapter().apply {
            itemClickListener = object : FavouriteRecipesAdapter.ItemClickListener<FavouriteRecipes>{
                override fun onItemClicked(item: FavouriteRecipes, itemPosition: Int) {
                 findNavController().navigate(R.id.home_to_recipe_information, bundleOf("recipe_id" to item.id))
                }
            }
        }

        binding.viewPager2.apply {
            adapter = favouritesAdapter
        }
            homeViewModel.recipe.observe(viewLifecycleOwner){ recipe ->
                binding.recipeText.text = recipe.title
               Glide.with(this)
                   .load(recipe.imageUrl)
                   .placeholder(R.drawable.ic_bottom_nav_trivia)
                   .into(binding.recipeImage)
                binding.recipeImage.setOnClickListener { findNavController().navigate(R.id.home_to_recipe_information, bundleOf("recipe_id" to recipe.id)) }

            }
        homeViewModel.dbRecipes.observe(viewLifecycleOwner){
          favouritesAdapter.updateRecipes(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
