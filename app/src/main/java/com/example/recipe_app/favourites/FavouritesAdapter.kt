//package com.example.recipe_app.favourites
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.recipe_app.R
////import com.example.recipe_app.api.database.FavouriteRecipes
//import com.example.recipe_app.search.ItemClickListener
//
//class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesAdapterViewHolder>() {
//    private val items = ArrayList<FavouriteRecipes>()
//    var itemClickListener: ItemClickListener<FavouriteRecipes>? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesAdapterViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favourites_recycler_view_item, parent, false)
//        return FavouritesAdapterViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: FavouritesAdapterViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//    fun updateItems(favouriteRecipes: List<FavouriteRecipes>){
//        items.clear()
//        items.addAll(favouriteRecipes)
//        notifyDataSetChanged()
//    }
//    inner class FavouritesAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
//        private val recipeImage = view.findViewById<ImageView>(R.id.recipe_image)
//        private val recipeTitle = view.findViewById<TextView>(R.id.recipe_title)
//        private val favouriteButton = view.findViewById<ImageView>(R.id.favourite_button)
//        fun bind(favouriteRecipe: FavouriteRecipes){
//            Glide.with(recipeImage)
//                .load(favouriteRecipe.imageUrl)
//                .into(recipeImage)
//            recipeTitle.text = favouriteRecipe.title
//            recipeImage.setOnClickListener {
//                itemClickListener?.onItemClicked(favouriteRecipe,absoluteAdapterPosition, recipeImage)
//            }
//            favouriteButton.setOnClickListener {
//                itemClickListener?.onItemClicked(favouriteRecipe,absoluteAdapterPosition, favouriteButton)
//            }
//
//
//        }
//
//    }
//    interface ItemClickListener<T> {
//        fun onItemClicked(item: T, itemPosition: Int, clickedView: View)
//    }
//
//}