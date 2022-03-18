package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.isDisplayed
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf
import todoApp.framework.TestData.getDB

object StatisticFragment : FragmentInterface<StatisticFragment> {
    private val statisticLayout = withId(R.id.statistics_layout)
    private val activeTasks = withId(R.id.stats_active_text)
    private val completedTasks = withId(R.id.stats_completed_text)
    private val openDrawerBtn = withContentDescription("Open navigation drawer")

    fun assertStatisticsTasks() {
        if (getDB().isEmpty()) {
            allOf(
                statisticLayout,
                hasDescendant(withText(R.string.statistics_no_tasks))
            ).isDisplayed()
        } else {
            val completed = calculatePercent()
            allOf(
                activeTasks,
                withText("Active tasks: ${String.format("%.1f", 100 - completed)}%")
            ).isDisplayed()
            allOf(
                completedTasks,
                withText("Completed tasks: ${String.format("%.1f", completed)}%")
            ).isDisplayed()
        }
    }

    fun openDrawer(){
        openDrawerBtn.click()
    }

    override fun fragIsDisplayed() = apply {
        statisticLayout.isDisplayed()
    }

    private fun calculatePercent(): Float {
        val db = getDB()
        var completed = 0f
        for (i in db) {
            if (i.isCompleted)
                completed++
        }
        return (completed / db.size) * 100
    }
}