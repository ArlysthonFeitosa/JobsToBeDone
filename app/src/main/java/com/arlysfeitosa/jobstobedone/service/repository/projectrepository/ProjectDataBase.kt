package com.arlysfeitosa.jobstobedone.service.repository.projectrepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel

@Database(entities = [ProjectModel::class], version = 1)
abstract class ProjectDataBase() : RoomDatabase() {

    abstract fun projectDAO(): ProjectDAO

    companion object {
        private lateinit var INSTANCE: ProjectDataBase

        fun getDataBase(context: Context): ProjectDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ProjectDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context, ProjectDataBase::class.java,
                        "projectDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}