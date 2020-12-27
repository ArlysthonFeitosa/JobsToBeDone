package com.arlysfeitosa.jobstobedone.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Projects")
class ProjectModel {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "project")
    var project:String = ""

    @ColumnInfo(name = "tasksCount")
    var tasksCount: Int = 0
}