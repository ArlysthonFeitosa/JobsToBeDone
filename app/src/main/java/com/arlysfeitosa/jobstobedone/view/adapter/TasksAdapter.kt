package com.arlysfeitosa.jobstobedone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.view.viewholder.TasksViewHolder

class TasksAdapter:RecyclerView.Adapter<TasksViewHolder>() {

    private var mList: List<TaskModel> = arrayListOf()
    private lateinit var mListener: TaskListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false)
        return TasksViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holderToday: TasksViewHolder, position: Int) {
        holderToday.bindData(mList[position])
    }

    fun attachListener(listener:TaskListener){
        this.mListener = listener
    }

    fun updateListener(list: List<TaskModel>){
        mList = list
        notifyDataSetChanged()
    }
}