package com.example.a3track.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R

class DataAdapterActivities(
    private val list: List<Activity>,
    private val listener: OnItemClickListener,
):  RecyclerView.Adapter<DataAdapterActivities.DataViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    private var onClickListener : View.OnClickListener? = null

    fun setOnClickListener(onClickListener: View.OnClickListener){
        this.onClickListener = onClickListener
    }

    // 1. user defined ViewHolder type
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val type: TextView = itemView.findViewById(R.id.ActivityType)
        val user: TextView = itemView.findViewById(R.id.User)
        val subType: TextView = itemView.findViewById(R.id.SubType)
        val time: TextView = itemView.findViewById(R.id.Time)
        val note: TextView = itemView.findViewById(R.id.Note)

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
        Log.i("XXX", "onCreateViewHolder $createCounter")
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_layout, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("Activities", "onBindViewHolder $bindCounter")
        // val currentItem = list.value!![position]
        val currentItem = list[position]
        holder.type.text = currentItem.Type.toString()
        holder.user.text = currentItem.User.toString()
        holder.subType.text = currentItem.SubType.toString()
        holder.time.text = currentItem.Time.toString()
        holder.note.text = currentItem.Note
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