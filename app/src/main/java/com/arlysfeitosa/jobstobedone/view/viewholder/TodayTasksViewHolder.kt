package com.arlysfeitosa.jobstobedone.view.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel

class TodayTasksViewHolder(itemView: View, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemView) {

    private val mCardView: CardView = itemView.findViewById(R.id.card_task)
    private var mTaskTitle: TextView = itemView.findViewById(R.id.text_task)
    private var mTaskProject: TextView = itemView.findViewById(R.id.text_taskProject)
    private var mTaskDateLimit: TextView = itemView.findViewById(R.id.text_date)
    private var mTaskSwitchComplete: Switch = itemView.findViewById(R.id.switch_complete)


    @SuppressLint("ResourceAsColor")
    fun bindData(task: TaskModel) {

        this.mTaskTitle.text = task.task
        this.mTaskProject.text = task.projectName
        this.mTaskDateLimit.text = task.date
        this.mTaskSwitchComplete.isEnabled = task.complete

        if (task.complete) {
            this.mCardView.setCardBackgroundColor(android.R.color.holo_green_dark)
        }

        mTaskSwitchComplete.setOnClickListener {
            if (task.complete) {
                listener.onUndoClick(task.id)
                this.mCardView.setCardBackgroundColor(R.color.DarkBlueVariant)
            } else {
                listener.onCompleteClick(task.id)
                this.mCardView.setCardBackgroundColor(android.R.color.holo_green_dark)
            }
        }
    }
}
