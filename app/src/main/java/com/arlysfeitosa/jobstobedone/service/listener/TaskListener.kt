package com.arlysfeitosa.jobstobedone.service.listener

interface TaskListener {
    fun onCompleteClick(id: Int)
    fun onUndoClick(id: Int)
    fun onDeleteClick(id: Int):Boolean
}