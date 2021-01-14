package com.arlysfeitosa.jobstobedone.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.TaskListener
import com.arlysfeitosa.jobstobedone.service.model.TaskModel
import com.arlysfeitosa.jobstobedone.view.adapter.TasksAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks.*
import java.text.SimpleDateFormat
import java.util.*

class TasksFragment() : Fragment() {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_tasks, container, false)

        //Initialize ViewlModel
        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
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

    private fun loadSpinner() {
        val locale: Locale = Locale.getDefault()
        val language = locale.language

        val spinnerEntriesPt = listOf<String>("Afazer", "Expiradas", "Todas")
        val spinnerEntriesEn = listOf<String>("To Do", "Overdue", "All")

        val spinnerAdapter = if (language == "pt") {
            ArrayAdapter(
                activity!!.applicationContext,
                R.layout.layout_spinner,
                spinnerEntriesPt
            )
        } else {
            ArrayAdapter(
                activity!!.applicationContext,
                R.layout.layout_spinner,
                spinnerEntriesEn
            )
        }

        spinner_task_filter.adapter = spinnerAdapter
        spinner_task_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedFragment: Fragment =
                    ToDoFragment()
                when(position) {
                    0 -> {
                        selectedFragment = ToDoFragment()
                    }

                    1 -> {
                        selectedFragment = OverdueFragment()
                    }

                    2 -> {
                        selectedFragment = AllFragment()
                    }
                }
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragment_container_tasks,
                    selectedFragment
                ).commit()
            }
        }
    }
}
