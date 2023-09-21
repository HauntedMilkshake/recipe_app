package com.example.recipe_app.instructions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.data.AnalyzedRecipe

class RecipeInstructionsPagerAdapter: RecyclerView.Adapter<RecipeInstructionsPagerAdapter.RecipesInstructionPager2ViewHolder>() {
    private val items = ArrayList<AnalyzedRecipe.Instruction>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipesInstructionPager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager_instructions, parent, false))

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecipesInstructionPager2ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateInstructions(newInstructions:List<AnalyzedRecipe.Instruction>){
        items.clear()
        items.addAll(newInstructions)
        notifyDataSetChanged()
    }
    class RecipesInstructionPager2ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val stepNumber = view.findViewById<TextView>(R.id.step_number)
        val stepDescription = view.findViewById<TextView>(R.id.step_description)
        fun bind(instruction: AnalyzedRecipe.Instruction){
            stepNumber.text = instruction.stepNumber.toString()
            stepDescription.text = instruction.instruction
        }
    }
}