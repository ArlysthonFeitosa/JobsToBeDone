package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository

class InsightsViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository = Repository(mContext)

    private var mAllTasksCount = MutableLiveData<Int>()
    val allTasksCount: LiveData<Int> = mAllTasksCount

    private var mDoneTasksCount = MutableLiveData<Int>()
    val doneTasksCount: LiveData<Int> = mDoneTasksCount

    private var mExpiredOrToDoTasksCount = MutableLiveData<Int>()
    val expiredOrToDoTasksCount: LiveData<Int> = mExpiredOrToDoTasksCount

    private var mAllProjects = MutableLiveData<List<ProjectModel>>()
    val allProjects: LiveData<List<ProjectModel>> = mAllProjects

    fun getAllTasksCount() {
        mAllTasksCount.value = mRepository.getAllTasksCount()
    }

    fun getDoneTasksCount() {
        mDoneTasksCount.value = mRepository.getDoneTasksCount()
    }

    fun getExpiredOrToDoTasksCount() {
        mExpiredOrToDoTasksCount.value = mRepository.getExpiredOrToDoTasksCount()
    }

    fun getAllProjects(){
        mAllProjects.value = mRepository.getAllProjects()
    }

}