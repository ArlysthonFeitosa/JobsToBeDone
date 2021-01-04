@file:Suppress("DEPRECATION")

package com.arlysfeitosa.jobstobedone.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.arlysfeitosa.jobstobedone.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.intellij.lang.annotations.Language
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("CommitTransaction", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.background = null

        //Default Fragment
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.fragment_container,
            TasksFragment()
        ).commit()


        //Bottom Navigation Bar - Item Click event
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment =
                TasksFragment() //Default
            when (it.itemId) {
                R.id.menu_tasks -> selectedFragment =
                    TasksFragment()
                R.id.menu_insights -> selectedFragment =
                    InsightsFragment()
            }

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit()
            //spinner_task_filter.adapter = arrayAdapter
            true
        }

        fab.setOnClickListener {
            startActivity(Intent(this, ChooseCreateActivity::class.java))
        }

    }
}

