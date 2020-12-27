package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mRepository = Repository(mContext)

    private var mTodayTasks = MutableLiveData<List<TaskModel>>()
    val todayTasks: LiveData<List<TaskModel>> = mTodayTasks

    private var mTomorrowTasks = MutableLiveData<List<TaskModel>>()
    val tomorrowTasks: LiveData<List<TaskModel>> = mTomorrowTasks

    private var mAfterTasks = MutableLiveData<List<TaskModel>>()
    val afterTasks: LiveData<List<TaskModel>> = mAfterTasks

    private var mExpiredTasks = MutableLiveData<List<TaskModel>>()
    val expiredTasks: LiveData<List<TaskModel>> = mExpiredTasks

    fun getTodayTasks(currentDate: String) {
        mTodayTasks.value = mRepository.getTodayTasks(currentDate)
    }

    fun getTomorrowTasks(currentDate: String) {

    }

    fun getAfterTasks(currentDate: String) {

    }

    fun getExpiredTasks(currentDate: String) {

    }
}