package com.example.a3track.model

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DataAdapter(
    private val list: List<Task>,
    private val listener: OnItemClickListener,
):  RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

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
        val project: TextView = itemView.findViewById(R.id.project)
        val title: TextView = itemView.findViewById(R.id.taskTitle)
        val assigner: TextView = itemView.findViewById(R.id.asigner)
        val time: TextView = itemView.findViewById(R.id.time)
        val assignee: TextView = itemView.findViewById(R.id.assigneeWho)
        val deadline: TextView = itemView.findViewById(R.id.deadlineWhen)
        val priority: TextView = itemView.findViewById(R.id.prio)
        val description: TextView = itemView.findViewById(R.id.description)
        val status: TextView = itemView.findViewById(R.id.tag)
        val percentage: TextView = itemView.findViewById(R.id.progressPercentage)

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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("Tasks", "onBindViewHolder ${bindCounter}")
        // val currentItem = list.value!![position]
        val currentItem = list[position]
        holder.title.text = currentItem.title
        holder.assigner.text = currentItem.assigner.toString()
        holder.time.text = currentItem.time.toString()
        holder.assignee.text = currentItem.assignee.toString()
        holder.deadline.text = currentItem.deadline.toString()
        holder.priority.text = currentItem.priority.toString()
        holder.description.text = currentItem.description
        holder.status.text = currentItem.status.toString()
        holder.percentage.text = currentItem.percentage.toString()

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


