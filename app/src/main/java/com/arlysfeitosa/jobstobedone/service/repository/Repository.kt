package com.arlysfeitosa.jobstobedone.service.repository

import android.content.Context
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.projectrepository.ProjectDataBase
import com.arlysfeitosa.jobstobedone.service.repository.taskrepository.TaskDataBase
import java.text.SimpleDateFormat
import java.util.*

class Repository(context: Context) {

    private val mTaskDataBase = TaskDataBase.getDataBase(context).taskDAO()
    private val mProjectDataBase = ProjectDataBase.getDataBase(context).projectDAO()

    fun checkExistsProjects(): Boolean {
        val allProjects: List<String>? = mProjectDataBase.getAllProjectNames()
        return !allProjects.isNullOrEmpty()
    }

    fun getTask(id: Int): TaskModel {
        return mTaskDataBase.getTask(id)
    }

    fun saveTask(task: TaskModel): Boolean {
        return mTaskDataBase.save(task) > 0
    }

    fun getAllTasks(): List<TaskModel>{
        return mTaskDataBase.getAllTasks()
    }

    fun updateTask(task: TaskModel): Boolean {
        return mTaskDataBase.update(task) > 0
    }

    fun deleteTask(id: Int){
        val task = mTaskDataBase.getTask(id)
        mTaskDataBase.delete(task)
    }

    fun getProject(projectName: String): ProjectModel {
        return mProjectDataBase.getProject(projectName)
    }

    fun saveProject(project: ProjectModel): Boolean {
        return mProjectDataBase.save(project) > 0
    }

    fun updateProject(project: ProjectModel): Boolean {
        return mProjectDataBase.update(project) > 0
    }

    fun deleteProject(project: ProjectModel) {
        mProjectDataBase.delete(project)
        mTaskDataBase.deleteTasksWhereProject(project.project)
    }

    fun getAllProjectNames(): List<String> {
        return mProjectDataBase.getAllProjectNames()
    }

    fun getAllProjects(): List<ProjectModel> {
        return mProjectDataBase.getAllProjects()
    }

    fun getTodayTasks(currentDate: String): List<TaskModel> {
        return mTaskDataBase.getTodayTasks(currentDate)
    }

    fun getTomorrowTasks(dateFormat: String): List<TaskModel> {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        val dateFormater = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val tomorrowDate = dateFormater.format(calendar.time)

        return mTaskDataBase.getTomorrowTasks(tomorrowDate)
    }

    fun getAfterTasks(dateFormat: String): List<TaskModel> {

        val nextSevenDaysList: MutableList<String> = mutableListOf<String>()
        val dateFormater = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        var date: String = "00/00/0000"

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_MONTH, 1) //Can't count tomorrow

        for (i in 0..5) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            date = dateFormater.format(calendar.time)
            nextSevenDaysList.add(i, date)
        }

        return mTaskDataBase.getAfterTasks(nextSevenDaysList)
    }

    fun getExpiredTasks(dateFormat: String):List<TaskModel> {
        val lastSevenDaysList: MutableList<String> = mutableListOf<String>()
        val dateFormater = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        var date: String = "00/00/0000"

        val calendar = Calendar.getInstance()
        calendar.time = Date()//Can't count tomorrow

        for (i in 0..6) {
            calendar.roll(Calendar.DAY_OF_MONTH, false)
            calendar.time
            date = dateFormater.format(calendar.time)
            lastSevenDaysList.add(i, date)
        }

        return mTaskDataBase.getOverdueTasks(lastSevenDaysList)
    }

    fun getAllTasksCount(): Int {
        return mTaskDataBase.getAllTasksCount()
    }

    fun getDoneTasksCount(): Int {
        return mTaskDataBase.getDoneTasksCount()
    }

    fun getExpiredOrToDoTasksCount(): Int {
        return mTaskDataBase.getExpiredOrToDoTasksCount()
    }

}