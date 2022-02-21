package todoApp.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.isChecked
import com.atiurin.ultron.extensions.isDisplayed
import com.atiurin.ultron.extensions.isNotChecked
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.Matchers.allOf
import todoApp.framework.FragDisplayed

object MainTaskScreen : FragDisplayed<MainTaskScreen> {

    private val toolbar = withId(R.id.toolbar)
    private val toolbarTitle = withText("Todo")
    private val menuFilterBtn = withId(R.id.menu_filter)
    private val menuBtn = withContentDescription("More options")
    private val drawerBtn = withContentDescription("Open navigation drawer")
    private val filteringText = withId(R.id.filtering_text)
    private val tasksList = withId(R.id.tasks_list)
    private val completeCheckBox = withId(R.id.complete_checkbox)
    private val taskTitle = withId(R.id.title_text)
    private val addTaskBtn = withId(R.id.add_task_fab)
    private val recyclerView = withRecyclerView(R.id.tasks_list)

    fun openAddTaskFragment() {
        addTaskBtn.click()
    }

    fun openDrawerFragment() {
        drawerBtn.click()
    }

    fun openTaskDetails(task: Task) {
        assertTaskDisplayed(task)
        getTaskItem(task).click()
    }

    fun assertTaskDisplayed(task: Task) {
        if (task.isCompleted)
            getTaskItem(task).getChild(completeCheckBox).isChecked()
        else
            getTaskItem(task).getChild(completeCheckBox).isNotChecked()
    }

    fun assertFilterActiveTasks(){
        allOf(filteringText,withText(R.string.label_active)).isDisplayed()
    }

    private fun getTaskItem(task: Task): UltronRecyclerViewItem {
        val matcher = allOf(
            hasDescendant(
                allOf(taskTitle, withText(task.title))
            )
        )
        return recyclerView.item(matcher)
    }


    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }
}