package com.example.a3track.model

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a3track.R
import com.example.a3track.models.ActivityModel
import com.example.a3track.models.DepartmentModel
import com.example.a3track.models.TaskModel

class DataAdapterActivities(
    private var list: MutableList<ActivityModel>,
    private val listener: OnItemClickListener,
):  RecyclerView.Adapter<DataAdapterActivities.DataViewHolder>(){
    private val departmentList: MutableList<DepartmentModel> = mutableListOf()
    private val taskList: MutableList<TaskModel> = mutableListOf()

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
        val type: TextView = itemView.findViewById(R.id.activityType)
        val user: TextView = itemView.findViewById(R.id.userName)
        val time: TextView = itemView.findViewById(R.id.createdAt)
        val note: TextView = itemView.findViewById(R.id.activityShortDesciption)
        val image: ImageView = itemView.findViewById(R.id.userProfile)

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
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("Activities", "onBindViewHolder $bindCounter")
        // val currentItem = list.value!![position]
        val currentItem = list[position]

        when(currentItem.activityType) {
            0 -> holder.type.text = "Department"
            1 -> holder.type.text = "Task"
            2 -> holder.type.text = "Comment"
            else -> holder.type.text = "Unknown"
        }

        if(currentItem.createdByUser?.image != null) {
            Glide.with(holder.image.context)
                .load(currentItem.createdByUser?.image)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.editprofil)
        }

        val time = currentItem.createdTime
        val date = java.util.Date(time)
        val format = java.text.SimpleDateFormat("MMM dd")
        val formatted = format.format(date)
        holder.time.text = formatted

        holder.user.text = currentItem.createdByUser?.first_name + " " + currentItem.createdByUser?.last_name

        // @TODO: if you want to show task details as in the design

        if(currentItem.activitySubType == 0) {
            val department = departmentList.find { it.ID == currentItem.activityTypeSubId }
            if(department != null) {
                holder.note.text = currentItem.createdByUser?.first_name + " " + currentItem.createdByUser?.last_name +
                        " added " +
                        if(currentItem.assignedToId != null) {
                            currentItem.assignedToId?.first_name + " " + currentItem.assignedToId?.last_name
                        } else {
                            "Unknown User"
                        } +
                        " to " + department.name + " group"
            }
        }

        if(currentItem.activitySubType == 1 || currentItem.activitySubType == 2) {
            currentItem.activityTypeSubId = 4
            val task = taskList.find { it.ID == currentItem.activityTypeSubId }
            if(task != null) {
                holder.note.text = currentItem.createdByUser?.first_name + " " + currentItem.createdByUser?.last_name +
                        " added " +
                        if(currentItem.assignedToId != null) {
                            currentItem.assignedToId?.first_name + " " + currentItem.assignedToId?.last_name
                        } else {
                            "Unknown User"
                        } +
                        " to " + task.title + " task"
            }
        }
    }

    // 4.
    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDate(newList: MutableList<ActivityModel>, newDepartmentList: MutableList<DepartmentModel>, newTaskList: MutableList<TaskModel>){
        list = newList
        departmentList.clear()
        departmentList.addAll(newDepartmentList)

        taskList.clear()
        taskList.addAll(newTaskList)

        notifyDataSetChanged()
    }


    companion object{
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }

}