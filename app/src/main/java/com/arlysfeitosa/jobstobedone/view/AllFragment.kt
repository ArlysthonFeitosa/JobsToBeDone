package com.arlysfeitosa.jobstobedone.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.view.adapter.TasksAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.fragment_overdue.*
import java.text.SimpleDateFormat
import java.util.*

class AllFragment : Fragment() {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var mListener: TaskListener
    private val mAllTasksAdapter = TasksAdapter()
    private lateinit var dateFormater: SimpleDateFormat
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_all, container, false)

        //Initialize ViewlModel
        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        //Initialize SimpleDateFormat
        dateFormater = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)

        attachCurrentDate() //Attach current date to ViewModel
        attachDateFormat(getString(R.string.date_format)) //Attach Simple Date Format to ViewModel
        startListener() //Initialize Recycler Listener
        formatRecycler() //Format Recyclers
        observer()

        mViewModel.load()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAllTasksAdapter.notifyDataSetChanged()
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

            override fun onDeleteClick(id: Int): Boolean {
                var mReturn: Boolean = false
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
                    mReturn = true
                }
                alert.setNegativeButton(getString(R.string.alert_delete_negative), null)
                alert.setOnCancelListener {

                }
                alert.show()
                return mReturn
            }
        }
    }

    private fun formatRecycler() {
        val recyclerAll = root.findViewById<RecyclerView>(R.id.recycler_all)
        recyclerAll.layoutManager = LinearLayoutManager(context)
        recyclerAll.adapter = mAllTasksAdapter
        recyclerAll.isNestedScrollingEnabled = false
        mAllTasksAdapter.attachListener(mListener)
    }

    private fun checkTasks() {
        text_allTasks.isVisible = !mViewModel.allTasks.value.isNullOrEmpty()
    }

    private fun observer() {
        mViewModel.allTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!mAllTasksAdapter.updateListener(it)) {
                Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show()
            }
            checkTasks()
        })
    }

}