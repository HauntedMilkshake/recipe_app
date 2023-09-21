package com.example.recipe_app.instructions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.data.Instructions

class RecipeInstructionsPagerAdapter: RecyclerView.Adapter<RecipeInstructionsPagerAdapter.RecipesInstructionPager2ViewHolder>() {
    private val items = ArrayList<Instructions>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipesInstructionPager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager_instructions, parent, false))

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecipesInstructionPager2ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateInstructions(newInstructions:List<Instructions>){
        items.clear()
        items.addAll(newInstructions)
        notifyDataSetChanged()
    }
    class RecipesInstructionPager2ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val stepNumber = view.findViewById<TextView>(R.id.step_number)
        private val stepDescription = view.findViewById<TextView>(R.id.step_description)
        fun bind(instruction: Instructions){
            stepNumber.text = instruction.number.toString()
            stepDescription.text = instruction.step
        }
    }
}