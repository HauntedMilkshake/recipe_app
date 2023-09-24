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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.recipe_app.R
import com.example.recipe_app.databinding.FragmentRecipeInformationBinding

class FragmentRecipeInformation: Fragment() {
    private var _binding: FragmentRecipeInformationBinding? = null
    private val binding get() = _binding!!
    private val informationViewModel: FragmentRecipeInformationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        informationViewModel.checkIfRecipeExistsAndGet(findNavController().previousBackStackEntry?.destination?.id!!, arguments?.getInt("recipe_id")!!)
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
        informationViewModel.isFavourited.observe(viewLifecycleOwner){favourited ->
            binding.apply{
                if(favourited){
                    favouriteButton.setImageResource(R.drawable.ic_favourite_recipe)
                    favouriteButton.setOnClickListener {
                        informationViewModel.removeFromFavourites(arguments?.getInt("recipe_id")!!)
                    }
                }else{
                    favouriteButton.setImageResource(R.drawable.ic_unfavourite_recipe)
                    favouriteButton.setOnClickListener {
                        informationViewModel.favouriteRecipes()
                    }
                }
            }
        }
        informationViewModel.enhancedRecipe.observe(viewLifecycleOwner){ recipe ->
                Glide.with(this)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.recipeImage)
            if(!recipe.isVegetarian){
                binding.vegeterian.visibility = View.GONE
            }else{
                binding.vegeterian.visibility = View.VISIBLE
                binding.vegeterian.text = "Vegetarian"
            }
            if(!recipe.isDairyFree){
                binding.dairy.visibility = View.GONE
            }else{
                binding.dairy.visibility = View.VISIBLE
                binding.dairy.text = "Dairy Free"
            }
            if(!recipe.isVegan){
                binding.vegan.visibility = View.GONE
            }else{
                binding.vegan.visibility = View.VISIBLE
                binding.vegan.text = "Vegan"
            }
            if(!recipe.isGlutenFree){
                binding.glutenfree.visibility = View.GONE
            }else{
                binding.glutenfree.visibility = View.VISIBLE
                binding.glutenfree.text = "Gluten free"
            }
            binding.recipeTitle.text = recipe.title

        }
        binding.apply{
            backButton.setOnClickListener{
                findNavController().navigate(R.id.information_to_home)
            }
            instructionsButton.setOnClickListener {
                findNavController().navigate(R.id.information_to_instructions, bundleOf("recipe_id" to arguments?.getInt("recipe_id")))
            }
        }

        informationViewModel.vladiMirishe.observe(viewLifecycleOwner){
            binding.apply {
                webView.settings.javaScriptEnabled = true
                webView.loadData(it, "text/html", "UTF-8")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}