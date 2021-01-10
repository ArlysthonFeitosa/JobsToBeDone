package com.arlysfeitosa.jobstobedone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.viewmodel.InsightsViewModel
import kotlinx.android.synthetic.main.fragment_insights.*

class InsightsFragment : Fragment() {

    private lateinit var mViewModel: InsightsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_insights, container, false)

        mViewModel = ViewModelProvider(this).get(InsightsViewModel::class.java)
        observe()
        loadFirstCardData()
        return root
    }

    private fun loadFirstCardData(){
        mViewModel.getAllTasksCount()
        mViewModel.getDoneTasksCount()
        mViewModel.getExpiredOrToDoTasksCount()
    }

    private fun updateAllTasks(count: Int) {
        value_all_firstcard.text = count.toString()
    }

    private fun updateDoneTasks(count: Int) {
        value_done_firstcard.text = count.toString()
    }

    private fun updadeExpiredOrToDoTasks(count: Int) {
        value_expired_firstcard.text = count.toString()
    }

    private fun observe() {
        mViewModel.allTasksCount.observe(viewLifecycleOwner, Observer {
            updateAllTasks(it)
        })
        mViewModel.doneTasksCount.observe(viewLifecycleOwner, Observer {
            updateDoneTasks(it)
        })
        mViewModel.expiredOrToDoTasksCount.observe(viewLifecycleOwner, Observer {
            updadeExpiredOrToDoTasks(it)
        })
    }
}