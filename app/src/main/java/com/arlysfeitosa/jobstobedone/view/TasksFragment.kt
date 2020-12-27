package com.arlysfeitosa.jobstobedone.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.view.adapter.TodayTasksAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.TaskFormViewModel
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.layout_task.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class TasksFragment : Fragment() {

    private lateinit var mDateFormat: SimpleDateFormat
    private lateinit var mViewModel: TasksViewModel
    private lateinit var mListener: TaskListener
    private val mTaskAdapter = TodayTasksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)

        mDateFormat = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)
        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        val todayTasksRecycler = root.findViewById<RecyclerView>(R.id.recycler_today)
        todayTasksRecycler.layoutManager = LinearLayoutManager(context)
        todayTasksRecycler.adapter = mTaskAdapter

        val date:Date = Date()
        var currentDate = mDateFormat.format(date)


        mListener = object :TaskListener{
            @SuppressLint("ResourceAsColor")
            override fun onCompleteClick(id: Int) {
                layout_itemTask.setBackgroundColor(android.R.color.holo_green_dark)
            }

            @SuppressLint("ResourceAsColor")
            override fun onUndoClick(id: Int) {
                layout_itemTask.setBackgroundColor(R.color.DarkBlueVariant)
            }
        }

        mTaskAdapter.attachListener(mListener)
        mViewModel.getTodayTasks(currentDate)

        observer()
        return root
    }

    private fun observer() {
        mViewModel.todayTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mTaskAdapter.updateListener(it)
        })
    }
}