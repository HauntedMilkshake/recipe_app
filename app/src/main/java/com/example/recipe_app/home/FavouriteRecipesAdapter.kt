package com.example.recipe_app.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.api.database.FavouriteRecipes

class FavouriteRecipesAdapter: RecyclerView.Adapter<FavouriteRecipesAdapter.FavouritesRecipeAdapter2ViewHolder>() {
    private val items = ArrayList<FavouriteRecipes>()
    var itemClickListener: ItemClickListener<FavouriteRecipes>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavouritesRecipeAdapter2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favourite_recipes_adapter_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavouritesRecipeAdapter2ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun updateRecipes(newRecipes: List<FavouriteRecipes>){
        items.clear()
        items.addAll(newRecipes)
        notifyDataSetChanged()
    }
    inner class FavouritesRecipeAdapter2ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val recipeImage = view.findViewById<ImageView>(R.id.recipe)
        fun bind(recipe: FavouriteRecipes){
           Glide.with(recipeImage)
               .load(recipe.imageUrl)
               .placeholder(R.drawable.placeholder)
               .into(recipeImage)
            recipeImage.setOnClickListener {
                itemClickListener?.onItemClicked(recipe, absoluteAdapterPosition)
            }
        }
    }
    interface ItemClickListener<T> {
        fun onItemClicked(item: T, itemPosition: Int)
    }
}