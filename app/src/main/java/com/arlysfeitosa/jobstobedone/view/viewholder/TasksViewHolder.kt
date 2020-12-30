package com.arlysfeitosa.jobstobedone.view.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.R.drawable
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel

class TasksViewHolder(itemView: View, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemView) {

    private var mTaskTitle: TextView = itemView.findViewById(R.id.text_task)
    private var mTaskProject: TextView = itemView.findViewById(R.id.text_taskProject)
    private var mTaskDateLimit: TextView = itemView.findViewById(R.id.text_date)
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var mTaskSwitchComplete: Switch = itemView.findViewById(R.id.switch_complete)
    private var mLayoutItem:RelativeLayout = itemView.findViewById(R.id.layout_itemTask)


    @SuppressLint("ResourceAsColor")
    fun bindData(task: TaskModel) {

        this.mTaskTitle.text = task.task
        this.mTaskProject.text = task.projectName
        this.mTaskDateLimit.text = task.date
        this.mTaskSwitchComplete.isChecked = task.complete

        mTaskSwitchComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                listener.onCompleteClick(task.id)
            } else {
                listener.onUndoClick(task.id)
            }
        }
    }
}