package com.arlysfeitosa.jobstobedone.service.repository.taskrepository

import androidx.room.*
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel

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

}