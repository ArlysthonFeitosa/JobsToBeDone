package com.arlysfeitosa.jobstobedone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.viewmodel.TaskFormViewModel

class TaskFormActivity : AppCompatActivity() {

    private lateinit var mViewModel: TaskFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

    }
}