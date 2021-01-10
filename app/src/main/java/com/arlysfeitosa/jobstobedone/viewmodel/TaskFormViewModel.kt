package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository = Repository(mContext)

    private var mSaveTask = MutableLiveData<Boolean>()
    val saveTask: LiveData<Boolean> = mSaveTask

    private var mTask = MutableLiveData<TaskModel>()
    val task: LiveData<TaskModel> = mTask

    private var mAllProjectNames = MutableLiveData<List<String>>()
    val allProjectNames: LiveData<List<String>> = mAllProjectNames

    fun saveTask(taskId:Int = 0, projectName: String = "", taskName: String, complete: Boolean = false, dateLimit: String) {
        val task:TaskModel = TaskModel().apply {
            this.id = taskId
            this.projectName = projectName
            this.task = taskName
            this.complete = complete
            this.date = dateLimit
        }
        mRepository.saveTask(task)
        val project = mRepository.getProject(projectName)
        project.tasksCount++
        mRepository.updateProject(project)
    }

    fun load(id:Int){
        mTask.value = mRepository.getTask(id)
    }

    fun refreshTasks(){

    }

    fun getAllProjectNames(){
        mAllProjectNames.value =  mRepository.getAllProjectNames()
    }
}