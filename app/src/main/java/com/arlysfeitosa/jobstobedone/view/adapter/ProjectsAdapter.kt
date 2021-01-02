package com.arlysfeitosa.jobstobedone.view.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.ProjectListener
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.view.viewholder.ProjectsViewHolder

class ProjectsAdapter:RecyclerView.Adapter<ProjectsViewHolder>() {

    private var mList: List<ProjectModel> = arrayListOf()
    private lateinit var mListener: ProjectListener
    private lateinit var mStrTaskCount:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_project, parent, false)
        return ProjectsViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holderToday: ProjectsViewHolder, position: Int) {
        holderToday.bindData(mList[position], mStrTaskCount)
    }

    fun attachListener(listener:ProjectListener){
        this.mListener = listener
    }

    fun updateListener(list: List<ProjectModel>, mStrTaskCount: String){
        attachStrTaskCount(mStrTaskCount)
        mList = list
    }

    private fun attachStrTaskCount(mStrTaskCount: String){
        this.mStrTaskCount = mStrTaskCount
    }

}