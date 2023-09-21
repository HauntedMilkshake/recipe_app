package com.example.recipe_app.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.recipe_app.R
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
            homeViewModel.recipe.observe(viewLifecycleOwner){ recipe ->
                binding.recipeText.text = recipe.title
               Glide.with(this)
                   .load(recipe.imageUrl)
                   //.transition(DrawableTransitionOptions.withCrossFade())
                   .into(binding.recipeImage)
                Log.d("IDTOSEND", recipe.id.toString())
                binding.recipeImage.setOnClickListener { findNavController().navigate(R.id.home_to_recipe_information, bundleOf("recipe_id" to recipe.id)) }

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
