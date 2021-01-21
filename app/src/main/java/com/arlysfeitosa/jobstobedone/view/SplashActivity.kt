package com.arlysfeitosa.jobstobedone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.arlysfeitosa.jobstobedone.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed(object :Runnable{
            override fun run() {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }, 3000)
    }
}