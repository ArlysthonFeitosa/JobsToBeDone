package com.arlysfeitosa.jobstobedone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arlysfeitosa.jobstobedone.R
import kotlinx.android.synthetic.main.fragment_tasks.*
import java.lang.Exception
import java.util.*
import kotlin.math.log

class TasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        return root
    }
}