package com.arlysfeitosa.jobstobedone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.view.viewholder.TodayTasksViewHolder

class TodayTasksAdapter:RecyclerView.Adapter<TodayTasksViewHolder>() {

    private var mList: List<TaskModel> = arrayListOf()
    private lateinit var mListener: TaskListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTasksViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false)
        return TodayTasksViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: TodayTasksViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    fun attachListener(listener:TaskListener){
        this.mListener = listener
    }

    fun updateListener(list: List<TaskModel>){
        mList = list
        notifyDataSetChanged()
    }
}