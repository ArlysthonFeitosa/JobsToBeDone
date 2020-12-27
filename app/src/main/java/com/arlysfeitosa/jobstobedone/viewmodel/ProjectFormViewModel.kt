package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository
import java.lang.Exception

class ProjectFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository: Repository = Repository(mContext)

    private var mSaveProject = MutableLiveData<Boolean>()
    val saveProject: LiveData<Boolean> = mSaveProject

    private var mProject = MutableLiveData<ProjectModel>()
    val project: LiveData<ProjectModel> = mProject

    fun saveProject(projectName: String) {
        try {
            val project = ProjectModel().apply {
                this.project = projectName
                this.tasksCount = 0
            }
            mSaveProject.value = mRepository.saveProject(project)
        } catch (e:Exception){
            mSaveProject.value = false
        }
    }

    fun load(projectName: String) {
        mProject.value = mRepository.getProject(projectName)
    }
}