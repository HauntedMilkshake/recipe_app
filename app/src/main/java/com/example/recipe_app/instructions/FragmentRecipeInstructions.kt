package com.example.recipe_app.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_app.databinding.FragmentRecipeInstructionsBinding

class FragmentRecipeInstructions: Fragment() {
    private var _binding: FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!
    private val instructionsViewModel: FragmentInstructionsViewModel by viewModels()
    private var viewPagerAdapter = RecipeInstructionsPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instructionsViewModel.fetchRecipeInformationById(arguments?.getInt("recipe_id")!!)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{

            instructionsViewpager.adapter = viewPagerAdapter
            instructionsViewModel.enhancedRecipe.observe(viewLifecycleOwner){recipe ->
                viewPagerAdapter.updateInstructions(recipe.instructions)
            }
        }

    }
}