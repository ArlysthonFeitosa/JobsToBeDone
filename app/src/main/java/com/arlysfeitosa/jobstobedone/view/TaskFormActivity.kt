package com.arlysfeitosa.jobstobedone.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.viewmodel.TaskFormViewModel
import kotlinx.android.synthetic.main.activity_project_form.*
import kotlinx.android.synthetic.main.activity_task_form.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private lateinit var mViewModel: TaskFormViewModel
    private var mDateSelected = true
    private lateinit var mDateFormat: SimpleDateFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mDateFormat = SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        mViewModel.getAllProjectNames()
        observe()
        button_date_picker.setOnClickListener(this)
        button_save_task.setOnClickListener(this)
    }

    private fun loadSpinner(listProjectNames: List<String>) {
        val adapterSpinner = ArrayAdapter(this, R.layout.layout_spinner, listProjectNames)
        spinner_projects.adapter = adapterSpinner
    }

    private fun observe() {
        mViewModel.allProjectNames.observe(this, Observer {
            loadSpinner(it)
        })
    }

    private fun checkAllCamps(): Boolean {
        return !(edit_task.text.toString() == "" || !mDateSelected)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_date_picker) {
            showDatePicker()
        } else if (v.id == R.id.button_save_task) {
            if (checkAllCamps()) {
                mViewModel.saveTask(
                    taskName = edit_task.text.toString(),
                    dateLimit = button_date_picker.text.toString(),
                    projectName = spinner_projects.selectedItem.toString()
                )
                finish()
            } else {
                toast("Fill in all entries.")
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        button_date_picker.text = mDateFormat.format(calendar.time)
        mDateSelected = true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinner_projects.setSelection(position)
    }
}

