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
import com.example.a3track.models.UserModel

class DataAdapterUsers(
    private var list: MutableList<UserModel>,
    private val listener: OnItemClickListener,
):  RecyclerView.Adapter<DataAdapterUsers.DataViewHolder>(){
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    // 1. user defined ViewHolder type
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val userName : TextView = itemView.findViewById<TextView>(R.id.userName)
        val userImage: ImageView = itemView.findViewById<ImageView>(R.id.profilePictureUser)

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
            LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        Log.i("Activities", "onBindViewHolder $bindCounter")
        // val currentItem = list.value!![position]
        val currentItem = list[position]
        holder.userName.text = currentItem.first_name + " " + currentItem.last_name
        if(currentItem.image != null){
            Glide.with(holder.userImage.context).load(currentItem.image).into(holder.userImage)
        }
    }

    // 4.
    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDate(newList: MutableList<UserModel>){
        list = newList
        notifyDataSetChanged()
    }


    companion object{
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }

}