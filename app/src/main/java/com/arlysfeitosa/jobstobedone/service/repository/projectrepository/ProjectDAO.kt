package com.arlysfeitosa.jobstobedone.service.repository.projectrepository

import androidx.room.*
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel

@Dao
interface ProjectDAO {

    @Insert
    fun save(project: ProjectModel): Long

    @Update
    fun update(project: ProjectModel): Int

    @Delete
    fun delete(project: ProjectModel)

    @Query("SELECT * FROM projects WHERE id = :id")
    fun getProject(id: Int): ProjectModel
}