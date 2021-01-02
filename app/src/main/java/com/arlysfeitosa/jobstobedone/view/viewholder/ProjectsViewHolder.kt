package com.arlysfeitosa.jobstobedone.view.viewholder

import android.app.Application
import android.content.ContentResolver
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.ProjectListener
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel

class ProjectsViewHolder(itemView: View, val listener: ProjectListener) :
    RecyclerView.ViewHolder(itemView) {

    private var mProjectTitle: TextView = itemView.findViewById(R.id.text_project)
    private var mProjectCount: TextView = itemView.findViewById(R.id.text_task_count)
    private var mButtonDelete: ImageView = itemView.findViewById(R.id.button_delete_project)

    fun bindData(project: ProjectModel, strTaskCount:String) {
        this.mProjectTitle.text = project.project
        this.mProjectCount.text = strTaskCount + " " + project.tasksCount.toString()

        mButtonDelete.setOnClickListener {
            listener.onDeleteClick(project.project)
        }
    }
}
