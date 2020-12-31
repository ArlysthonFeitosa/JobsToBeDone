package com.arlysfeitosa.jobstobedone.service.repository.projectrepository

import androidx.room.*
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.service.model.TaskModel

@Dao
interface ProjectDAO {

    @Insert
    fun save(project: ProjectModel): Long

    @Update
    fun update(project: ProjectModel): Int

    @Delete
    fun delete(project: ProjectModel)

    @Query("SELECT * FROM projects WHERE project = :projectName")
    fun getProject(projectName: String): ProjectModel

    @Query("SELECT project FROM projects")
    fun getAllProjectNames(): List<String>
}