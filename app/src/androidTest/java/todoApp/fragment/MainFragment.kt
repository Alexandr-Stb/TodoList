package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.Matchers.allOf
import todoApp.framework.TestData.editedTask
import todoApp.framework.TestData.getDB

object MainFragment : FragmentInterface<MainFragment> {

    private val toolbar = withId(R.id.toolbar)
    private val toolbarTitle = withText("Todo")
    private val drawerBtn = withContentDescription("Open navigation drawer")
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
        assertTaskInUIAndDB(task)
        recyclerView.lastItem().click()
    }

    fun assertAllTasks() {
        assertTaskInUIAndDB(getDB().first())
        assertTaskInUIAndDB(getDB().last())
    }

    fun assertTaskInUIAndDB(task: Task) {
        getTaskItem(assertTaskInDB(task.title)!!.title).isDisplayed()
        assertCheckBox(task)
        assertTaskInDB(task.title)
    }

    fun assertTaskInUI(){
        getTaskItem(editedTask.title).isDisplayed()
    }

    fun assertFilterTasks(completeTasks: Boolean) {
        getDB().forEach {
            if (it.isCompleted == completeTasks)
                getTaskItem(it.title).isDisplayed()
        }
    }

    fun assertClearCompletedTasks() {
        getDB().forEach {
            getTaskItem(it.title)
                .getChild(completeCheckBox).isNotChecked()
        }
    }

    fun assertTaskStateChange() {
        val firstTaskDB = getDB().first()
        val firstTask = getTaskItem(firstTaskDB.title)
        firstTask.getChild(completeCheckBox).click()
        if (firstTask.getChild(completeCheckBox).isSuccess { withTimeout(1000).isChecked() }) {
            firstTask.getChild(completeCheckBox).click()
        }
        if (firstTaskDB.isCompleted)
            firstTask.getChild(completeCheckBox).isNotChecked()
        else
            firstTask.getChild(completeCheckBox).isChecked()
    }

    private fun assertTaskInDB(title: String): Task? {
        getDB().forEach {
            if (it.title == title)
                return it
        }
        return null
    }

    private fun getTaskItem(title: String): UltronRecyclerViewItem {
        val matcher = allOf(
            hasDescendant(
                allOf(taskTitle, withText(title))
            )
        )
        return recyclerView.item(matcher)
    }

    private fun assertCheckBox(task: Task) {
        if (task.isCompleted)
            getTaskItem(task.title)
                .getChild(completeCheckBox).isChecked()
        else
            getTaskItem(task.title)
                .getChild(completeCheckBox).isNotChecked()
    }

    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }
}