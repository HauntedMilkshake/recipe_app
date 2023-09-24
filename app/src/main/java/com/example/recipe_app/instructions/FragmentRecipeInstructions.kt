package com.example.recipe_app.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.databinding.FragmentRecipeInstructionsBinding

class FragmentRecipeInstructions: Fragment() {
    private var _binding: FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!
    private val instructionsViewModel: FragmentInstructionsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeInstructionsBinding.inflate(inflater, container, false)
        instructionsViewModel.checkIfInformationExistsAndInitializeInformation(arguments?.getInt("recipe_id")!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = RecipeInstructionsPagerAdapter()
        val ingredientAdapter = RecipeIngredientsAdapter()

        binding.apply{
            recipeIngredientsRecyclerView.apply {
                adapter = ingredientAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            instructionsViewpager.adapter = viewPagerAdapter
            instructionsViewModel.instructions.observe(viewLifecycleOwner){ instructions ->
                viewPagerAdapter.updateInstructions(instructions)
            }
            instructionsViewModel.ingredient.observe(viewLifecycleOwner){
                ingredientAdapter.updateIngredients(it)
            }
        }
    }
}