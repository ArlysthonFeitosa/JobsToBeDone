package com.arlysfeitosa.jobstobedone.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.listener.ProjectListener
import com.arlysfeitosa.jobstobedone.view.adapter.ProjectsAdapter
import com.arlysfeitosa.jobstobedone.viewmodel.ProjectFormViewModel
import kotlinx.android.synthetic.main.activity_project_form.*

class ProjectFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ProjectFormViewModel
    private lateinit var mListener: ProjectListener
    private val mProjectsAdapter = ProjectsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_form)

        mViewModel = ViewModelProvider(this).get(ProjectFormViewModel::class.java)
        button_save_project.setOnClickListener(this)
        attachListener()
        formatRecycler()
        observe()
        mViewModel.load()
    }

    private fun formatRecycler(){
        val projectsRecycler = findViewById<RecyclerView>(R.id.recycler_projects)
        projectsRecycler.layoutManager = LinearLayoutManager(this)
        projectsRecycler.adapter = mProjectsAdapter

        mProjectsAdapter.attachListener(mListener)
    }

    private fun attachListener() {
        mListener = object : ProjectListener {
            override fun onDeleteClick(projectName: String) {

                //mViewModel.deleteProject(projectName)
                //mViewModel.load()

                val alertProject = AlertDialog.Builder(this@ProjectFormActivity)
                alertProject.setTitle(getString(R.string.alert_delete_project_title))
                alertProject.setMessage(getString(R.string.alert_delete_project_message))
                alertProject.setPositiveButton(getString(R.string.alert_delete_positive)) { _, _ ->
                    mViewModel.deleteProject(projectName)
                    mViewModel.load()
                }
                alertProject.setNeutralButton(getString(R.string.alert_delete_negative), null)
                alertProject.show()
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save_project) {
            if (verifyData()) {
                val projectName = edit_project.text.toString()
                mViewModel.saveProject(projectName)
                mViewModel.load()
            } else {
                Toast.makeText(this, getString(R.string.project_name_alert), Toast.LENGTH_LONG)
            }
        }
    }


    private fun verifyData(): Boolean {
        return this.edit_project.text.toString() != ""
    }

    private fun observe() {
        mViewModel.saveProject.observe(this, Observer {
            if (!it) {
                Toast.makeText(this, getString(R.string.project_name_error), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        mViewModel.allProjects.observe(this, Observer {
            mProjectsAdapter.updateListener(it, getString(R.string.task_count))
            mProjectsAdapter.notifyDataSetChanged()
        })
    }
}