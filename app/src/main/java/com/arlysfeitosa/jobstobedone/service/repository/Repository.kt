package com.arlysfeitosa.jobstobedone.service.repository

import android.content.Context
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.projectrepository.ProjectDataBase
import com.arlysfeitosa.jobstobedone.service.repository.taskrepository.TaskDataBase

class Repository(context: Context) {

    private val mTaskDataBase = TaskDataBase.getDataBase(context).taskDAO()
    private val mProjectDataBase = ProjectDataBase.getDataBase(context).projectDAO()

    fun getTask(id: Int): TaskModel {
        return mTaskDataBase.getTask(id)
    }

    fun saveTask(task: TaskModel): Boolean {
        return mTaskDataBase.save(task) > 0
    }

    fun updateTask(task: TaskModel): Boolean {
        return mTaskDataBase.update(task) > 0
    }

    fun deleteTask(task: TaskModel) {
        mTaskDataBase.delete(task)
    }




    fun getProject(projectName:String): ProjectModel {
        return mProjectDataBase.getProject(projectName)
    }

    fun saveProject(project: ProjectModel): Boolean {
        return mProjectDataBase.save(project) > 0
    }

    fun updateProject(project: ProjectModel): Boolean {
        return mProjectDataBase.update(project) > 0
    }

    fun deleteProject(project: ProjectModel) {
        mProjectDataBase.delete(project)
    }

    fun getAllProjects(): List<String>{
        return mProjectDataBase.getAllProjects()
    }
}