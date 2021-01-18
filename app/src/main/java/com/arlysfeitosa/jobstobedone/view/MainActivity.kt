package com.arlysfeitosa.jobstobedone.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import com.arlysfeitosa.jobstobedone.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("CommitTransaction")
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

            true
        }

        fab.setOnClickListener {
            startActivity(Intent(this, ChooseCreateActivity::class.java))
        }

        //text_toolbar.setTypeface(Typeface.createFromAsset(applicationContext.assets.open, "lobster_regular.ttf"))
    }
}

