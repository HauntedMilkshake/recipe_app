package com.example.recipe_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


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
            homeViewModel.recipe.observe(viewLifecycleOwner){
                recipeOfTheDayText.text = it.title
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
    private val _recipe = MutableLiveData<BareRecipe>()
     val recipe: LiveData<BareRecipe> get() = _recipe
    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
                val result = api.getSingleRecipe()
                _recipe.postValue(result ?: BareRecipe(id=1, title="sex", image="idc"))
                Log.d("RECIPE", result.toString())
        }
    }
}