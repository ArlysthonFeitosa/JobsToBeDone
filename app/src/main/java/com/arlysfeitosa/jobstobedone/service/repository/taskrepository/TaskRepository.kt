package com.arlysfeitosa.jobstobedone.service.repository.taskrepository

import android.content.Context
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.projectrepository.ProjectDataBase

class TaskRepository(context: Context) {

    private val mDataBase = TaskDataBase.getDataBase(context).taskDAO()

    fun getProject(id: Int): TaskModel {
        return mDataBase.getTask(id)
    }

    fun save(task: TaskModel): Boolean {
        return mDataBase.save(task) > 0
    }

    fun update(task: TaskModel): Boolean {
        return mDataBase.update(task) > 0
    }

    fun delete(task: TaskModel) {
        mDataBase.delete(task)
    }
}