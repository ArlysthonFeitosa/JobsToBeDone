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
        pieChart_secondcard.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 30f
            transparentCircleRadius = 35f
            setHoleColor(Color.WHITE)
        }

        val pieEntry: ArrayList<PieEntry> = ArrayList<PieEntry>()

        val allTasksCount: Int = mViewModel.allTasksCount.value!!

        var projectTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if (allTasksCount > 0 && projectLists[a].tasksCount > 0) {
                projectTaskCount = ((projectLists[a].tasksCount * 100) / allTasksCount).toFloat()
                pieEntry.add(PieEntry(projectTaskCount, projectLists[a].project))
            }
        }

        val pieDataColors = listOf<Int>(
            Color.rgb(192, 255, 140),
            Color.rgb(255, 247, 140),
            Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255),
            Color.rgb(255, 140, 157),
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53),
            Color.rgb(64, 89, 128),
            Color.rgb(149, 165, 124),
            Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134),
            Color.rgb(179, 48, 80)
        )

        val pieDataSet: PieDataSet = PieDataSet(pieEntry, "").apply {
            colors = pieDataColors
            sliceSpace = 3f
            selectionShift = 5f
            valueTextColor = R.color.TurquoiseBlue
            valueTextSize = 15f
        }

        pieChart_secondcard.data = PieData(pieDataSet)
        if (pieEntry.isEmpty()) pieChart_secondcard.isVisible = false
    }

    private fun loadThirdCard(projectLists: List<ProjectModel>) {
        pieChart_thirdcard.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 30f
            transparentCircleRadius = 35f
            setHoleColor(Color.WHITE)
        }

        val pieEntry: ArrayList<PieEntry> = ArrayList<PieEntry>()

        val DoneTasksCount: Int = mViewModel.doneTasksCount.value!!

        var projectDoneTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if (DoneTasksCount > 0 && projectLists[a].doneTasksCount > 0) {
                projectDoneTaskCount =
                    ((projectLists[a].doneTasksCount * 100) / DoneTasksCount).toFloat()
                pieEntry.add(PieEntry(projectDoneTaskCount, projectLists[a].project))
            }
        }

        val pieDataColors = listOf<Int>(
            Color.rgb(192, 255, 140),
            Color.rgb(255, 247, 140),
            Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255),
            Color.rgb(255, 140, 157),
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53),
            Color.rgb(64, 89, 128),
            Color.rgb(149, 165, 124),
            Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134),
            Color.rgb(179, 48, 80)
        )

        val pieDataSet: PieDataSet = PieDataSet(pieEntry, "").apply {
            colors = pieDataColors
            sliceSpace = 3f
            selectionShift = 5f
            valueTextColor = R.color.TurquoiseBlue
            valueTextSize = 15f
        }

        pieChart_thirdcard.data = PieData(pieDataSet)
        if (pieEntry.isEmpty()) pieChart_thirdcard.isVisible = false
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
            loadThirdCard(it)
        })
    }
}