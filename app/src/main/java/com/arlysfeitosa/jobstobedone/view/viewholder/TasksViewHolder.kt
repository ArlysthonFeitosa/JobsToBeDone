package com.arlysfeitosa.jobstobedone.view.viewholder

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.VoiceInteractor
import android.media.MediaCodec
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.ImageView
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
    private var mCompleteIcon: Switch = itemView.findViewById(R.id.switch_complete)
    private var mTaskId: Int = 0

    fun bindData(task: TaskModel) {
        this.mTaskTitle.text = task.task
        this.mTaskProject.text = task.projectName
        this.mTaskDateLimit.text = task.date
        this.mCompleteIcon.isChecked = task.complete
        this.mTaskId = task.id

        mCompleteIcon.setOnCheckedChangeListener { buttonView, isChecked ->
            synchronized(this) {
                if (isChecked) {
                    if (task.complete == false) {
                        listener.onCompleteClick(task.id)
                    } else {
                        mCompleteIcon.isChecked = true
                    }

                } else if (!isChecked) {
                    if (task.complete == true) {
                        listener.onUndoClick(task.id)
                    } else {
                        mCompleteIcon.isChecked = false
                    }
                }
            }
        }

        itemView.setOnLongClickListener {
            synchronized(this) {
                listener.onDeleteClick(task.id)
                true
            }
        }
    }
}
