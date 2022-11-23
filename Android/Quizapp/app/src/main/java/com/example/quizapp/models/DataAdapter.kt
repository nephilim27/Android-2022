package com.example.quizapp.models

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.ItemList
import com.example.quizapp.R
import com.example.quizapp.itemList
import com.example.quizapp.ui.QuestionListFragment
import com.google.android.material.snackbar.Snackbar

// Item event handling


class DataAdapter(
    private val list: MutableList<ItemList> = itemList,
    private val listener: OnItemClickListener,
    private val detailsListener: detailsItem,
    private val deleteListener: deleteItem
):  RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface detailsItem {
        fun detailsItem(position: Int)
    }

    interface deleteItem {
        fun deleteItem(position: Int)
    }

    private var onClickListener : OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    // 1. user defined ViewHolder type
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        OnClickListener {
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
        var details: Button = itemView.findViewById(R.id.detailsButton)
        var delete: Button = itemView.findViewById(R.id.deleteButton)
        // Constructor
        init{
            itemView.setOnClickListener( this )

            details.setOnClickListener {
                val currentPos = this.adapterPosition
                detailsListener.detailsItem(currentPos);
            }

            delete.setOnClickListener {
                val snack = Snackbar.make(it, "Are you sure you want to delete this item?", Snackbar.LENGTH_SHORT)
                snack.setAction("YES", View.OnClickListener {
                    val currentPos = this.adapterPosition
                    deleteListener.deleteItem(currentPos)
                })
                snack.show()

            }
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