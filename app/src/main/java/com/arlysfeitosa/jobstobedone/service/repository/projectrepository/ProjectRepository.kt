package com.arlysfeitosa.jobstobedone.service.repository.projectrepository

import android.content.Context
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel

class ProjectRepository(context: Context) {

    private val mDataBase = ProjectDataBase.getDataBase(context).projectDAO()

    fun getProject(id: Int): ProjectModel {
        return mDataBase.getProject(id)
    }

    fun save(project: ProjectModel): Boolean {
        return mDataBase.save(project) > 0
    }

    fun update(project: ProjectModel): Boolean {
        return mDataBase.update(project) > 0
    }

    fun delete(project: ProjectModel) {
        mDataBase.delete(project)
    }
}