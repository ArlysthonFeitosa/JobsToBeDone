package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository

class ChooseCreateViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository = Repository(mContext)

    fun checkExistsProjects(): Boolean {
        return mRepository.checkExistsProjects()
    }
}