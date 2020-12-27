package com.arlysfeitosa.jobstobedone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arlysfeitosa.jobstobedone.R

class InsightsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_insights, container, false)

        return root
    }
}