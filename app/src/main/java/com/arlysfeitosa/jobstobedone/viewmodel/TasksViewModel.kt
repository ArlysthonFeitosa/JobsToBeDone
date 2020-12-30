package com.arlysfeitosa.jobstobedone.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.Repository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

    private var currentDate: String = "00/00/0000"

    var dateFomat:String = "MM/dd/yyyy"

    //LocalDate datetime = LocalDate.parse(txt_Data_Abertura.getText(), DateTimeFormatter.ofPattern(“dd/MM/yyyy”))
    //String newstring = datetime.format(DateTimeFormatter.ofPattern(“dd/MM/yyyy”))

    fun attachDateFormat(dateFomatString: String){
        this.dateFomat = dateFomatString
    }

    fun attachCurrentDate(currentDate: String){
        this.currentDate = currentDate
    }

    fun getTask(id: Int): TaskModel {
        return mRepository.getTask(id)
    }

    fun getProject(projectName: String): ProjectModel {
        return mRepository.getProject(projectName)
    }

    fun refreshTasks() {
        mTodayTasks.value = mRepository.getTodayTasks(this.currentDate)
        mTomorrowTasks.value = mRepository.getTomorrowTasks(dateFomat)
    }

    fun getTodayTasks() {
        mTodayTasks.value = mRepository.getTodayTasks(this.currentDate)
    }

    fun getTomorrowTasks() {
        mTomorrowTasks.value = mRepository.getTomorrowTasks(dateFomat)
    }

    fun getAfterTasks(currentDate: String) {

    }

    fun getExpiredTasks(currentDate: String) {

    }

    fun complete(taskId: Int) {
        updateStatus(taskId, true)
    }

    fun undo(taskId: Int) {
        updateStatus(taskId, false)
    }

    private fun updateStatus(taskId: Int, complete: Boolean) {
        val task: TaskModel = getTask(taskId)
        task.complete = complete

        val project: ProjectModel = getProject(task.projectName)

        if (complete) {
            project.tasksCount++
        } else {
            project.tasksCount--
        }

        mRepository.updateTask(task)
        mRepository.updateProject(project)
        refreshTasks()
    }
}