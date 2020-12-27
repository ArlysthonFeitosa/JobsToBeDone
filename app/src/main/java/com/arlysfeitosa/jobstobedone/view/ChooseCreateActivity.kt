package com.arlysfeitosa.jobstobedone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.arlysfeitosa.jobstobedone.R
import kotlinx.android.synthetic.main.activity_choose_create.*

class ChooseCreateActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_create)


        button_project.setOnClickListener(this)
        button_task.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_project){
            startActivity(Intent(this, ProjectFormActivity::class.java))
            finish()
        }else if(v.id == R.id.button_task){
            startActivity(Intent(this, TaskFormActivity::class.java))
            finish()
        }
    }
}