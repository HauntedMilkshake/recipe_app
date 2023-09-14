package com.example.recipe_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.data.DomainRecipe
import com.example.recipe_app.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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
        binding.apply {
            recipeOfTheDayText.text = homeViewModel.getRecipe().title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
class FragmentHomeViewModel: ViewModel() {
    private lateinit var api: RetrofitRecipeApiService
    private lateinit var recipe: DomainRecipe
    init{
      runBlocking {
         launch {
             recipe = api.getSingleRecipe("pasta")
        }
      }
    }
    fun getRecipe(): DomainRecipe = recipe
}