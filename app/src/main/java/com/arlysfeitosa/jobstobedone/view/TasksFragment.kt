package com.arlysfeitosa.jobstobedone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.viewmodel.TaskFormViewModel
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class TasksFragment : Fragment() {

    private lateinit var mDateFormat: SimpleDateFormat
    private lateinit var mViewModel: TasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)

        mDateFormat = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)
        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        observer()
        return root
    }

    private fun observer() {
        mViewModel.todayTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })
    }
}