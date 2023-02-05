package com.example.a3track.model

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.models.TaskModel

class TaskDataAdapter(
    private var list: MutableList<TaskModel>,
    private val listener: OnItemClickListener,
):  RecyclerView.Adapter<TaskDataAdapter.DataViewHolder>(){

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
        val assignee: TextView = itemView.findViewById(R.id.assigneeWho)
        val deadline: TextView = itemView.findViewById(R.id.deadlineWhen)
        val priority: TextView = itemView.findViewById(R.id.prio)
        val description: TextView = itemView.findViewById(R.id.description)
        val status: TextView = itemView.findViewById(R.id.tag)
        val percentage: TextView = itemView.findViewById(R.id.progressPercentage)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)


    }

    // 3. Called many times, when we scroll the list
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("Tasks", "onBindViewHolder $bindCounter")
        // val currentItem = list.value!![position]
        val currentItem = list[position]
        holder.project.text = currentItem.departmentID?.name + " project"
        holder.title.text = currentItem.title

        @SuppressLint("SimpleDateFormat") val timeFormat = java.text.SimpleDateFormat("hh:mm a")
        val createdTime = timeFormat.format(currentItem.created_time * 1000)

        if (currentItem.created_by_user_ID != null) {
            val text =
                currentItem.created_by_user_ID?.first_name + " " + currentItem.created_by_user_ID?.last_name + " " + createdTime
            holder.assigner.text = text
        } else {
            holder.assigner.text = "N/A $createdTime"
        }

        if (currentItem.assigned_to_user_ID != null) {
            val text = "<b>" + currentItem.assigned_to_user_ID?.first_name +
                    " " + currentItem.assigned_to_user_ID?.last_name + "</b>"
            holder.assignee.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            holder.assignee.text = HtmlCompat.fromHtml("<b>No user assigned</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        when(currentItem.status) {
            0 -> {
                holder.status.text = "Not Started"
            }
            1 -> {
                holder.status.text = "In Progress"
            }
            2 -> {
                holder.status.text = "Blocked"
            }
            3 -> {
                holder.status.text = "Done"
            }
            else -> {
                holder.status.text = "Not Started"
            }
        }


        val deadline = currentItem.deadline
        if (deadline != 0L) {
            // format deadline to human readable as dd/mm/yyyy
            @SuppressLint("SimpleDateFormat") val dateFormat =
                java.text.SimpleDateFormat("MMM dd yyyy")
            val deadlineDate = dateFormat.format(deadline * 1000)
            val text = "<b>$deadlineDate</b>"
            holder.deadline.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            holder.deadline.text = "N/A"
        }

        when(currentItem.priority) {
            0 -> {
                holder.priority.text = "Low priority"
            }
            1 -> {
                holder.priority.text = "Medium priority"
            }
            2 -> {
                holder.priority.text = "High priority"
            } else -> {
                holder.priority.text = "Low priority"
            }
        }

        if (holder.description.text.length > 200) {
            holder.description.text = holder.description.text.substring(0, 300) + "..."
        } else {
            holder.description.text = currentItem.description
        }

        if(currentItem.percentage != null) {
            holder.percentage.text = currentItem.percentage.toString() + "%"
            holder.progressBar.progress = currentItem.percentage!!
        } else {
            holder.percentage.text = "0%"
            holder.progressBar.progress = 0
        }
    }

    // 4.
    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: MutableList<TaskModel>) {
        list = newData
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): TaskModel {
        return list[position]
    }

    companion object{
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }
}


