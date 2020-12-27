package com.arlysfeitosa.jobstobedone.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tasks")
class TaskModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "task")
    var task: String = ""

    @ColumnInfo(name = "projectName")
    var projectName: String = ""

    @ColumnInfo(name = "complete")
    var complete: Boolean = false

    @ColumnInfo(name = "date")
    var date: String = "MM/dd/yyyy"
}