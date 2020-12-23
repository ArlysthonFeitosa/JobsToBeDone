package com.arlysfeitosa.jobstobedone.service.repository.taskrepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.service.repository.projectrepository.ProjectDataBase


@Database(entities = [TaskModel::class], version = 1)
abstract class TaskDataBase() : RoomDatabase() {

    abstract fun taskDAO(): TaskDAO

    companion object {
        private lateinit var INSTANCE: TaskDataBase

        fun getDataBase(context: Context): TaskDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ProjectDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context, TaskDataBase::class.java,
                        "taskDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}