package com.example.recipe_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: FragmentHomeViewModel by activityViewModels()
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
            try {
                recipeOfTheDayText.text = homeViewModel.getRecipe().title
            }catch (e: Exception){
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
class FragmentHomeViewModel(application: MyApplication): AndroidViewModel(application) {
    private var api: RetrofitRecipeApiService = application.apiService
    private lateinit var recipe: BareRecipe
    init{
      runBlocking {
         launch {
             recipe = api.getSingleRecipe()!!
             Log.d("RECIPE", recipe.toString())
        }
      }
    }
    fun getRecipe() = recipe
}