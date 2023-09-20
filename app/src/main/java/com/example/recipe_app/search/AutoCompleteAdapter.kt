package com.example.recipe_app.search


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.R
import com.example.recipe_app.data.AutoCompleteResult

class AutoCompleteAdapter : RecyclerView.Adapter<AutoCompleteAdapter.AutoCompleteViewHolder>() {

        private val items = ArrayList<AutoCompleteResult>()

        var itemClickListener: ItemClickListener<AutoCompleteResult>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoCompleteViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_autocomplete, parent, false)
            return AutoCompleteViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: AutoCompleteViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun updateItems(newItems: List<AutoCompleteResult>) {
            val oldList: List<AutoCompleteResult> = ArrayList(items)

            items.clear()
            items.addAll(newItems)

            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newItems[newItemPosition]
            }
            }).dispatchUpdatesTo(this)
        }
        inner class AutoCompleteViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            private val textView = view.findViewById<TextView>(R.id.autocomplete_text)
            fun bind(autoComplete: AutoCompleteResult) {
                Log.d("BINDING DATA", "${autoComplete.toString()}")
                textView.text = autoComplete.title
                textView.setOnClickListener{
                    itemClickListener?.onItemClicked(autoComplete, absoluteAdapterPosition)
                }
             }
        }
    }



interface ItemClickListener<T> {
    fun onItemClicked(item: T, itemPosition: Int)
}