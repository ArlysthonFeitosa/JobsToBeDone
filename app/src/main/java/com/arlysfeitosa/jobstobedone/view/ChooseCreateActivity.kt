package com.arlysfeitosa.jobstobedone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.viewmodel.ChooseCreateViewModel
import kotlinx.android.synthetic.main.activity_choose_create.*

class ChooseCreateActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ChooseCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_create)

        mViewModel = ViewModelProvider(this).get(ChooseCreateViewModel::class.java)

        button_project.setOnClickListener(this)
        button_task.setOnClickListener(this)
        button_delete_project.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_project) {
            startActivity(Intent(this, ProjectFormActivity::class.java))
            finish()
        } else if (v.id == R.id.button_task) {
            if (mViewModel.checkExistsProjects()){
                startActivity(Intent(this, TaskFormActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, getString(R.string.have_to_create_project), Toast.LENGTH_LONG).show()
            }
        } else if (v.id == R.id.button_delete_project) {
            startActivity(Intent(this, DeleteProjectActivity::class.java))
            finish()
        }
    }
}