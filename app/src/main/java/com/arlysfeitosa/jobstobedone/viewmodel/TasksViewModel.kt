package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository = Repository(mContext)

    var mTodayTasks = MutableLiveData<List<TaskModel>>()
    val todayTasks: LiveData<List<TaskModel>> = mTodayTasks

    private var mTomorrowTasks = MutableLiveData<List<TaskModel>>()
    val tomorrowTasks: LiveData<List<TaskModel>> = mTomorrowTasks

    private var mAfterTasks = MutableLiveData<List<TaskModel>>()
    val afterTasks: LiveData<List<TaskModel>> = mAfterTasks

    private var mExpiredTasks = MutableLiveData<List<TaskModel>>()
    val expiredTasks: LiveData<List<TaskModel>> = mExpiredTasks

    private var mAllTasks = MutableLiveData<List<TaskModel>>()
    val allTasks: LiveData<List<TaskModel>> = mAllTasks

    private var currentDate: String = "00/00/0000"
    var dateFomat: String = "MM/dd/yyyy"

    fun attachDateFormat(dateFomatString: String) {
        this.dateFomat = dateFomatString
    }

    fun attachCurrentDate(currentDate: String) {
        this.currentDate = currentDate
    }

    fun deleteTask(id: Int) {
        val projectToUpdate = mRepository.getProject(mRepository.getTask(id).projectName)
        projectToUpdate.tasksCount--
        mRepository.updateProject(projectToUpdate)
        mRepository.deleteTask(id)
    }

    fun load() {
        mTodayTasks.value = mRepository.getTodayTasks(this.currentDate)
        mTomorrowTasks.value = mRepository.getTomorrowTasks(dateFomat)
        mAfterTasks.value = mRepository.getAfterTasks(dateFomat)
        mExpiredTasks.value = mRepository.getExpiredTasks(dateFomat)
        mAllTasks.value = mRepository.getAllTasks()
    }

    fun getTask(id: Int): TaskModel {
        return mRepository.getTask(id)
    }

    fun getProject(projectName: String): ProjectModel {
        return mRepository.getProject(projectName)
    }

    fun getTodayTasks(): List<TaskModel> {
        val todayTasks: List<TaskModel> = mRepository.getTodayTasks(this.currentDate)
        mTodayTasks.value = todayTasks
        return todayTasks
    }

    fun getTomorrowTasks(): List<TaskModel> {
        val tomorrowTasks: List<TaskModel> = mRepository.getTomorrowTasks(dateFomat)
        mTomorrowTasks.value = tomorrowTasks
        return tomorrowTasks
    }

    fun getAfterTasks(): List<TaskModel> {
        val afterTasks: List<TaskModel> = mRepository.getAfterTasks(this.currentDate)
        mTomorrowTasks.value = afterTasks
        return afterTasks
    }

    fun getExpiredTasks(currentDate: String) {

    }

    fun complete(taskId: Int): Boolean {
        return updateStatus(taskId, true)
    }

    fun undo(taskId: Int): Boolean {
        return updateStatus(taskId, false)
    }

    //On click complete/undo
    private fun updateStatus(taskId: Int, complete: Boolean): Boolean {
        val task: TaskModel? = getTask(taskId)
        if (task?.complete != null && task.projectName != null) {
            task?.complete = complete

            val project: ProjectModel = getProject(task.projectName)

            if (complete) {
                project.doneTasksCount++
            } else if (project.doneTasksCount != 0) {
                project.doneTasksCount--
            }

            mRepository.updateTask(task)
            mRepository.updateProject(project)
            load()
            return true
        } else {
            return false
        }
    }
}