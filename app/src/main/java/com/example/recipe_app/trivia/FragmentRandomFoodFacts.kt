package com.example.recipe_app.trivia

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
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.databinding.FragmentRandomFoodFactsBinding
import com.example.recipe_app.search.ItemClickListener

class FragmentRandomFoodFacts: Fragment() {
    private var _binding: FragmentRandomFoodFactsBinding? = null

    private val binding get() = _binding!!
    private val triviaViewModel: FragmentRandomFoodFactsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomFoodFactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val triviaAdapter = TriviaAdapter().apply {
            itemClickListener = object : ItemClickListener<FoodDetect> {
                override fun onItemClicked(item: FoodDetect, itemPosition: Int) {
                    findNavController().navigate(R.id.action_trivia_to_search, bundleOf("autofill" to item.annotation))
                }
            }
        }
        binding.apply {
            tagViewPager.adapter = triviaAdapter
            tagViewPager.layoutManager = LinearLayoutManager(requireContext())
            resetButton.setOnClickListener {
                triviaViewModel.fetchTriviaAndDetectedFoods()
            }
        }
        triviaViewModel.trivia.observe(viewLifecycleOwner){
            binding.trivia.text = "Did you know that $it"
        }
        triviaViewModel.detectedFoods.observe(viewLifecycleOwner){
            binding.apply { triviaAdapter.updateItems(it) }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}