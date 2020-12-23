package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.taskrepository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mTaskRepository = TaskRepository(mContext)

    private var mSaveTask = MutableLiveData<Boolean>()
    val saveTask: LiveData<Boolean> = mSaveTask

    private var mTask = MutableLiveData<TaskModel>()
    val task: LiveData<TaskModel> = mTask

    fun saveTask(taskId:Int, projectId: Int, taskName: String, complete: Boolean, dateLimit: String) {
        val task:TaskModel = TaskModel().apply {
            this.id = taskId
            this.projectId = projectId
            this.task = taskName
            this.complete = complete
            this.date = dateLimit
        }
        mSaveTask.value = mTaskRepository.save(task)
    }

    fun load(id:Int){
        mTask.value = mTaskRepository.getTask(id)
    }
}