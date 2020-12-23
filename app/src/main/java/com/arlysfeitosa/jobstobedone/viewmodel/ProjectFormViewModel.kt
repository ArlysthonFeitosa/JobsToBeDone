package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.repository.projectrepository.ProjectRepository

class ProjectFormViewModel(application: Application): AndroidViewModel(application)  {

    private val mContext = application.applicationContext
    private var mProjectRepository:ProjectRepository = ProjectRepository(mContext)

    private var mSaveProject = MutableLiveData<Boolean>()
    val saveProject: LiveData<Boolean> = mSaveProject

    private var mProject = MutableLiveData<ProjectModel>()
    val project: LiveData<ProjectModel> = mProject

    fun saveProject(projectName:String){
        val project = ProjectModel().apply {
            this.id = 0
            this.project = projectName
            this.tasksCount = 0
        }
        mSaveProject.value = mProjectRepository.save(project)
    }

    fun load(id:Int){
        mProject.value = mProjectRepository.getProject(id)
    }
}