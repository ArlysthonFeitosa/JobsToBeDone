package com.arlysfeitosa.jobstobedone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.view.viewholder.TasksViewHolder

class TasksAdapter : RecyclerView.Adapter<TasksViewHolder>() {

    private var mList: List<TaskModel> = arrayListOf()
    private lateinit var mListener: TaskListener
    private var haveToNotify:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false)
        return TasksViewHolder(item)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holderToday: TasksViewHolder, position: Int) {
        val task = mList[position]
        holderToday.bindData(task)
        synchronized(this){
            holderToday.mComplete.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (task.complete == false) {
                        mListener.onCompleteClick(task.id)
                        try {
                            notifyItemChanged(holderToday.adapterPosition)
                        }catch (e:Exception){
                            mListener.onUndoClick(task.id)
                        }
                    } else {
                        holderToday.mComplete.isChecked = true
                    }

                } else if (!isChecked) {
                    if (task.complete == true) {
                        mListener.onUndoClick(task.id)
                        try {
                            notifyItemChanged(holderToday.adapterPosition)
                        }catch (e:Exception){
                            mListener.onCompleteClick(task.id)
                        }
                    } else {
                        holderToday.mComplete.isChecked = false
                    }
                }
            }

            holderToday.itemView.setOnLongClickListener {
                if (mListener.onDeleteClick(task.id)) {
                    notifyItemRemoved(holderToday.adapterPosition)

                }
                true
            }
        }
    }

    fun attachListener(listener: TaskListener) {
        this.mListener = listener
    }

    fun updateListener(list: List<TaskModel>): Boolean {

        if (mList.size != list.size) {
            haveToNotify = true
        }
        mList = list
        if(haveToNotify){
            try {
                synchronized(this){
                    notifyDataSetChanged()
                    haveToNotify = false
                }
            }catch (e:Exception){
                return false
            }
        }
        return true
    }
}