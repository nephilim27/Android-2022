package com.example.quizapp.models

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R


// Item event handling
interface OnItemClickListener{
    fun onItemClick(position: Int)
}

class DataAdapter (
    private val list: List<Item>,
    private val listener: OnItemClickListener
):  RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    // 1. user defined ViewHolder type
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        OnClickListener {
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
        // Constructor
        init{
            itemView.setOnClickListener( this )
        }

        override fun onClick(p0: View?) {
            // Delegate event handling to ListFragment
            val position: Int = adapterPosition
            if( position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }

        }
    }



    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        ++createCounter
        Log.i("XXX", "onCreateViewHolder ${createCounter}")
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("XXX", "onBindViewHolder ${bindCounter}")
        val currentItem = list[position]
        holder.textView1.text = currentItem.question
    }

    // 4.
    override fun getItemCount(): Int {
        return list.size
    }

    companion object{
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }

}