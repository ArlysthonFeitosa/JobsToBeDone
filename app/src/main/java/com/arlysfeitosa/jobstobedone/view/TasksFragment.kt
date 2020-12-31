package com.arlysfeitosa.jobstobedone.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.view.adapter.TasksAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder

class TasksFragment() : Fragment() {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var mListener: TaskListener
    private val mTodayTaskAdapter = TasksAdapter()
    private val mTomorrowTasksAdapter = TasksAdapter()
    private lateinit var dateFormater: SimpleDateFormat
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_tasks, container, false)

        //Initialize ViewlModel
        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        //Initialize SimpleDateFormat
        dateFormater = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)

        attachCurrentDate() //Attach current date to ViewModel
        attachDateFormat(getString(R.string.date_format)) //Attach Simple Date Format to ViewModel
        startListener() //Initialize Recycler Listener
        formatRecycler() //Format Recyclers
        observer()
        mViewModel.load() //Load Tasks

        return root
    }

    private fun attachCurrentDate() {
        mViewModel.attachCurrentDate(dateFormater.format(Date()))
    }

    private fun attachDateFormat(dateFomat: String) {
        mViewModel.attachDateFormat(dateFomat)
    }

    private fun formatRecycler() {
        val todayTasksRecycler = this.root.findViewById<RecyclerView>(R.id.recycler_today)
        todayTasksRecycler.layoutManager = LinearLayoutManager(context)
        todayTasksRecycler.adapter = mTodayTaskAdapter

        val tomorrowTasksRecycler = root.findViewById<RecyclerView>(R.id.recycler_tomorrow)
        tomorrowTasksRecycler.layoutManager = LinearLayoutManager(context)
        tomorrowTasksRecycler.adapter = mTomorrowTasksAdapter

        mTodayTaskAdapter.attachListener(mListener)
        mTomorrowTasksAdapter.attachListener(mListener)
    }

    private fun startListener() {
        mListener = object : TaskListener {
            override fun onCompleteClick(id: Int) {
                mViewModel.complete(id)
            }

            override fun onUndoClick(id: Int) {
                mViewModel.undo(id)
            }

            override fun onDeleteClick(id: Int) {
                val alert = AlertDialog.Builder(context)
                alert.setTitle(getString(R.string.alert_delete_title))
                alert.setMessage(getString(R.string.alert_delete_message))
                alert.setPositiveButton(getString(R.string.alert_delete_positive)) { _, _ ->
                    val task = mViewModel.getTask(id)
                    if(task.complete){
                        mViewModel.undo(id)
                    }
                    mViewModel.deleteTask(id)
                    mViewModel.load()
                }
                alert.setNeutralButton(getString(R.string.alert_delete_negative), null)
                alert.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load()
    }

    private fun observer() {
        mViewModel.todayTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mTodayTaskAdapter.updateListener(it)
            mTodayTaskAdapter.notifyDataSetChanged()
        })
        mViewModel.tomorrowTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mTomorrowTasksAdapter.updateListener(it)
            mTomorrowTasksAdapter.notifyDataSetChanged()
        })
    }
}