package com.arlysfeitosa.jobstobedone.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arlysfeitosa.jobstobedone.R
import com.arlysfeitosa.jobstobedone.service.model.ProjectModel
import com.arlysfeitosa.jobstobedone.viewmodel.InsightsViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_insights.*

class InsightsFragment : Fragment() {

    private lateinit var mViewModel: InsightsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_insights, container, false)

        mViewModel = ViewModelProvider(this).get(InsightsViewModel::class.java)
        observe()
        loadCardDatas()
        return root
    }

    private fun loadCardDatas() {
        mViewModel.getAllTasksCount()
        mViewModel.getDoneTasksCount()
        mViewModel.getExpiredOrToDoTasksCount()

        mViewModel.getAllProjects()
    }

    private fun loadSecondCard(projectLists: List<ProjectModel>) {
        pieChard_secondcard.setUsePercentValues(true)
        pieChard_secondcard.description.isEnabled = false
        pieChard_secondcard.legend.isEnabled = false
        pieChard_secondcard.isDrawHoleEnabled = true
        pieChard_secondcard.setHoleColor(Color.WHITE)

        val pieEntry: ArrayList<PieEntry> = ArrayList<PieEntry>()

        val allTasksCount: Int = mViewModel.allTasksCount.value!!

        var projectTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if(allTasksCount > 0){
                projectTaskCount = ((projectLists[a].tasksCount * 100) / allTasksCount).toFloat()
                pieEntry.add(PieEntry(projectTaskCount, projectLists[a].project))
            }
        }

        val teste = listOf<Int>(Color.rgb(193, 37, 82), android.graphics.Color.rgb(255, 102, 0), android.graphics.Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), android.graphics.Color.rgb(179, 100, 53))

        val pieDataSet: PieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.colors = teste
        pieDataSet.sliceSpace = 3f
        pieDataSet.selectionShift = 5f


        val pieData:PieData = PieData(pieDataSet)
        pieChard_secondcard.data = pieData
        if(pieEntry.isEmpty()){
            pieChard_secondcard.isVisible = false
        }
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
        mViewModel.allProjects.observe(viewLifecycleOwner, Observer {
            loadSecondCard(it)
        })
    }
}