package com.arlysfeitosa.jobstobedone.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.view.adapter.TasksAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks.*
import java.text.SimpleDateFormat
import java.util.*

class TasksFragment() : Fragment() {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var mListener: TaskListener
    private val mTodayTaskAdapter = TasksAdapter()
    private val mTomorrowTasksAdapter = TasksAdapter()
    private val mAfterTasksAdapter = TasksAdapter()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSpinner()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load()
    }

    private fun attachCurrentDate() {
        mViewModel.attachCurrentDate(dateFormater.format(Date()))
    }

    private fun attachDateFormat(dateFomat: String) {
        mViewModel.attachDateFormat(dateFomat)
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
                alert.setTitle(getString(R.string.alert_delete_task_title))
                alert.setMessage(getString(R.string.alert_delete_task_message))
                alert.setPositiveButton(getString(R.string.alert_delete_positive)) { _, _ ->
                    val task = mViewModel.getTask(id)
                    if (task.complete) {
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

    private fun formatRecycler() {
        val todayTasksRecycler = this.root.findViewById<RecyclerView>(R.id.recycler_today)
        todayTasksRecycler.layoutManager = LinearLayoutManager(context)
        todayTasksRecycler.adapter = mTodayTaskAdapter

        val tomorrowTasksRecycler = root.findViewById<RecyclerView>(R.id.recycler_tomorrow)
        tomorrowTasksRecycler.layoutManager = LinearLayoutManager(context)
        tomorrowTasksRecycler.adapter = mTomorrowTasksAdapter

        val afterTasksRecycler = root.findViewById<RecyclerView>(R.id.recycler_after)
        afterTasksRecycler.layoutManager = LinearLayoutManager(context)
        afterTasksRecycler.adapter = mAfterTasksAdapter

        mTodayTaskAdapter.attachListener(mListener)
        mTomorrowTasksAdapter.attachListener(mListener)
        mAfterTasksAdapter.attachListener(mListener)
    }

    private fun checkTasks() {
        text_today.isVisible = !mViewModel.todayTasks.value.isNullOrEmpty()
        text_tomorrow.isVisible = !mViewModel.tomorrowTasks.value.isNullOrEmpty()
        text_after.isVisible = !mViewModel.afterTasks.value.isNullOrEmpty()
    }

    private fun loadSpinner() {
        val locale: Locale = Locale.getDefault()
        val language = locale.language

        val spinnerEntriesPt = listOf<String>("Afazer", "Expiradas")
        val spinnerEntriesEn = listOf<String>("To Do", "Overdue")
        lateinit var spinnerAdapter: ArrayAdapter<String>

        if (language == "pt") {
            spinnerAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.layout_spinner, spinnerEntriesPt)
        } else {
            spinnerAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.layout_spinner, spinnerEntriesEn)
        }
        spinner_task_filter.adapter = spinnerAdapter
    }

    private fun observer() {
        mViewModel.todayTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mTodayTaskAdapter.updateListener(it)
            mTodayTaskAdapter.notifyDataSetChanged()
            checkTasks()
        })
        mViewModel.tomorrowTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mTomorrowTasksAdapter.updateListener(it)
            mTomorrowTasksAdapter.notifyDataSetChanged()
            checkTasks()
        })
        mViewModel.afterTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mAfterTasksAdapter.updateListener(it)
            mAfterTasksAdapter.notifyDataSetChanged()
            checkTasks()
        })
    }
}