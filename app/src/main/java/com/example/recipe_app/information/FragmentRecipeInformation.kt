package com.example.recipe_app.information

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
import com.example.recipe_app.databinding.FragmentRecipeInformationBinding

class FragmentRecipeInformation: Fragment() {
    private var _binding: FragmentRecipeInformationBinding? = null

    private val binding get() = _binding!!
    private val informationViewModel: FragmentRecipeInformationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        informationViewModel.getRecipe(arguments?.getInt("recipe_id")!!)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        informationViewModel.enhancedRecipe.observe(viewLifecycleOwner){ recipe ->
                Glide.with(this)
                    .load(recipe.imageUrl)
                    //.transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.recipeImage)
                //binding.recipeTitle.text = recipe.title + recipe.isDairyFree.toString()

        }
        binding.apply{
            backButton.setOnClickListener{
                // we have to find a way to know which fragment sent us back so that we can accurately pass the id of the recipe that we were sent from
                findNavController().navigate(R.id.information_to_home)
            }
            instructionsButton.setOnClickListener {
                findNavController().navigate(R.id.information_to_instructions, bundleOf("recipe_id" to arguments?.getInt("recipe_id")))
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}