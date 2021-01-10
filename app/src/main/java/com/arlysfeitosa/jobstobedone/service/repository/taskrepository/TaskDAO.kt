package com.arlysfeitosa.jobstobedone.service.repository.taskrepository

import androidx.room.*
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import java.util.*

@Dao
interface TaskDAO {

    @Insert
    fun save(task: TaskModel): Long

    @Update
    fun update(task: TaskModel): Int

    @Delete
    fun delete(task: TaskModel)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Int): TaskModel

    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTodayTasks(date: String): List<TaskModel>

    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTomorrowTasks(date: String): List<TaskModel>

    @Query("SELECT * FROM tasks WHERE date IN (:dateList) ")
    fun getAfterTasks(dateList: List<String>): List<TaskModel>

    @Query("SELECT COUNT(complete)  FROM tasks WHERE complete = 1")
    fun getDoneTasksCount(): Int

    @Query("SELECT COUNT(complete)  FROM tasks WHERE complete = 0")
    fun getExpiredOrToDoTasksCount(): Int

    @Query("SELECT COUNT(task) FROM tasks")
    fun getAllTasksCount(): Int

}