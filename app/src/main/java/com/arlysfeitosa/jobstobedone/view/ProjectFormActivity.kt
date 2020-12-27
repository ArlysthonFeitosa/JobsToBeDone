package com.arlysfeitosa.jobstobedone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.viewmodel.ProjectFormViewModel
import kotlinx.android.synthetic.main.activity_project_form.*

class ProjectFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ProjectFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_form)


        mViewModel = ViewModelProvider(this).get(ProjectFormViewModel::class.java)
        button_save_project.setOnClickListener(this)

        observe()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save_project) {
            if(verifyData()){
                val projectName = edit_project.text.toString()
                mViewModel.saveProject(projectName)
                finish()
            }else{
                Toast.makeText(this, getString(R.string.project_name_alert), Toast.LENGTH_LONG)
            }
        }
    }


    private fun verifyData(): Boolean {
        return this.edit_project.text.toString() != ""
    }

    private fun observe() {
        mViewModel.saveProject.observe(this, Observer {
            if(!it){
                Toast.makeText(this, getString(R.string.project_name_error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}