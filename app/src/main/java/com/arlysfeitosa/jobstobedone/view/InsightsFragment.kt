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
        text_count_secondcard.text = allTasksCount.toString()

        var projectTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if (allTasksCount > 0 && projectLists[a].tasksCount > 0) {
                projectTaskCount = ((projectLists[a].tasksCount * 100) / allTasksCount).toFloat()
                pieEntry.add(PieEntry(projectTaskCount, projectLists[a].project))
            }
        }

        val pieDataColors = listOf<Int>(
            Color.rgb(86, 126, 156),
            Color.rgb(116, 183, 232),
            Color.rgb(232, 150, 68),
            Color.rgb(162, 93, 232),
            Color.rgb(94, 160, 126),
            Color.rgb(66, 179, 149),
            Color.rgb(86, 252, 162),
            Color.rgb(1, 103, 149),
            Color.rgb(15, 155, 142),
            Color.rgb(16, 122, 176),
            Color.rgb(16, 166, 116),
            Color.rgb(123, 200, 246))

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

        val doneTasksCount: Int = mViewModel.doneTasksCount.value!!
        text_count_thirdcard.text = doneTasksCount.toString()

        var projectDoneTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if (doneTasksCount > 0 && projectLists[a].doneTasksCount > 0) {
                projectDoneTaskCount =
                    ((projectLists[a].doneTasksCount * 100) / doneTasksCount).toFloat()
                pieEntry.add(PieEntry(projectDoneTaskCount, projectLists[a].project))
            }
        }

        val pieDataColors = listOf<Int>(
            Color.rgb(86, 126, 156),
            Color.rgb(116, 183, 232),
            Color.rgb(232, 150, 68),
            Color.rgb(162, 93, 232),
            Color.rgb(94, 160, 126),
            Color.rgb(66, 179, 149),
            Color.rgb(86, 252, 162),
            Color.rgb(1, 103, 149),
            Color.rgb(15, 155, 142),
            Color.rgb(16, 122, 176),
            Color.rgb(16, 166, 116),
            Color.rgb(123, 200, 246))

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

    private fun loadFourthCard(projectLists: List<ProjectModel>) {
        pieChart_fourthcard.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 30f
            transparentCircleRadius = 35f
            setHoleColor(Color.WHITE)
        }

        val pieEntry: ArrayList<PieEntry> = ArrayList<PieEntry>()

        val expiredOrToDoTasksCount: Int = mViewModel.expiredOrToDoTasksCount.value!!
        text_count_fourthcard.text = expiredOrToDoTasksCount.toString()

        var projectExpiredOrToDoTaskCount: Float = 0f
        for (a in projectLists.indices) {
            if (expiredOrToDoTasksCount > 0 && (projectLists[a].tasksCount - projectLists[a].doneTasksCount > 0)) {
                projectExpiredOrToDoTaskCount =
                    (((projectLists[a].tasksCount - projectLists[a].doneTasksCount) * 100) / expiredOrToDoTasksCount).toFloat()
                pieEntry.add(PieEntry(projectExpiredOrToDoTaskCount, projectLists[a].project))
            }
        }

        val pieDataColors = listOf<Int>(
            Color.rgb(86, 126, 156),
            Color.rgb(116, 183, 232),
            Color.rgb(232, 150, 68),
            Color.rgb(162, 93, 232),
            Color.rgb(94, 160, 126),
            Color.rgb(66, 179, 149),
            Color.rgb(86, 252, 162),
            Color.rgb(1, 103, 149),
            Color.rgb(15, 155, 142),
            Color.rgb(16, 122, 176),
            Color.rgb(16, 166, 116),
            Color.rgb(123, 200, 246))

        val pieDataSet: PieDataSet = PieDataSet(pieEntry, "").apply {
            colors = pieDataColors
            sliceSpace = 3f
            selectionShift = 5f
            valueTextColor = R.color.TurquoiseBlue
            valueTextSize = 15f
        }

        pieChart_fourthcard.data = PieData(pieDataSet)
        if (pieEntry.isEmpty()) pieChart_fourthcard.isVisible = false
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
            loadFourthCard(it)
        })
    }
}