package com.example.recipe_app.trivia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.search.ItemClickListener
import com.google.android.material.textview.MaterialTextView

class TriviaAdapter: RecyclerView.Adapter<TriviaAdapter.TriviaAdapterViewHolder>() {
    private val items = ArrayList<FoodDetect>()
    var itemClickListener: ItemClickListener<FoodDetect>? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tag_view_pager_item, parent, false)
        return TriviaAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TriviaAdapterViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun updateItems(newItems: List<FoodDetect>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    inner class TriviaAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val foodText = view.findViewById<MaterialTextView>(R.id.tag)
        fun bind(food: FoodDetect){
            foodText.text = food.annotation
            foodText.setOnClickListener {
                itemClickListener?.onItemClicked(food, absoluteAdapterPosition)
            }
        }

    }
}