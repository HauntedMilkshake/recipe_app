package com.example.recipe_app.search;

import android.view.View;

public class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

        private val items = ArrayList<BareRecip>()

        var itemClickListener: ItemClickListener<BareRecipe>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recyclerview_search, parent, false)
            return SearchViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun updateItems(newItems: List<BareRecipe>) {
//            val oldList: List<BareRecipe> = ArrayList(items)
//
//            items.clear()
//            items.addAll(newItems)
//
////        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
////        override fun getOldListSize(): Int = oldList.size
////
////        override fun getNewListSize(): Int = newItems.size
////
////        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
////        return oldList[oldItemPosition].id == newItems[newItemPosition].id
////        }
////
////        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
////        return oldList[oldItemPosition] == newItems[newItemPosition]
////        }
////        }).dispatchUpdatesTo(this)
        }
        inner class SearchViewHolder(view:View) : RecyclerView.ViewHolder(view) {

            fun bind(searchResult: BareRecipe) {
                //TODO()
             }
        }
    }



interface ItemClickListener<T> {
    fun onItemClicked(item: T, itemPosition: Int)
}