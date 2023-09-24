package com.example.recipe_app.instructions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.data.Ingredients

class RecipeIngredientsAdapter: RecyclerView.Adapter<RecipeIngredientsAdapter.RecipesIngredientsPager2ViewHolder>() {
    private val items = ArrayList<Ingredients.Ingredient>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesIngredientsPager2ViewHolder = RecipesIngredientsPager2ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.recipe_ingredients_recycler_view_item), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecipesIngredientsPager2ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun updateIngredients(newIngr: List<Ingredients.Ingredient>){
        items.clear()
        items.addAll(newIngr)
        notifyDataSetChanged()
    }
    inner class RecipesIngredientsPager2ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val ingredientName = view.findViewById<TextView>(R.id.ingredient_name)
        private val amount = view.findViewById<TextView>(R.id.ingredient_amount)
        fun bind(ingr: Ingredients.Ingredient){
            ingredientName.text = ingr.name
            amount.text = ingr.amount.metric.value.toString() + ingr.amount.metric.unit
        }
    }

}