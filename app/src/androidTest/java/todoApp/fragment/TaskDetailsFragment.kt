package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import todoApp.framework.TestData.countTasks

object TaskDetailsFragment : FragmentInterface<TaskDetailsFragment> {
    private val toolbar = withId(R.id.toolbar)
    private val toolbarTitle = withText("Task Details")
    private val checkBox = withId(R.id.task_detail_complete_checkbox)
    private val titleTask = withId(R.id.task_detail_title_text)
    private val descriptionTask = withId(R.id.task_detail_description_text)
    private val deleteTaskBtn = withId(R.id.menu_delete)
    private val recyclerView = withRecyclerView(withId(R.id.tasks_list))
    private val titleTaskInList = withId(R.id.title_text)
    private val navigateUp = withContentDescription("Navigate up")
    private val editTaskBtn = withId(R.id.edit_task_fab)

    fun deleteTask() {
        deleteTaskBtn.click()
        MainFragment.fragIsDisplayed()
    }

    fun changeStateTask() {
        val completed = checkBox.isSuccess { isChecked() }
        Thread.sleep(1000)
        checkBox.click()

        if (completed)
            checkBox.isNotChecked()
        else
            checkBox.isChecked()

        navigateUp.click()
    }

    fun assertTaskState() {
        val firstTask = getDB().first()
        if(firstTask.isCompleted)
            recyclerView.firstItem().getChild(withId(R.id.complete_checkbox)).isChecked()
        else
            recyclerView.firstItem().getChild(withId(R.id.complete_checkbox)).isNotChecked()
    }

    fun assertDeleteTaskDB() {
        if (getDB().size == countTasks)
            recyclerView.firstItem().getChild(
                allOf(titleTaskInList, withText(getDB()[0].title))
            ).isDisplayed()
        else
            deleteTaskBtn.isDisplayed()
    }

    fun openTask() {
        recyclerView.firstItem().click()
    }

    fun assertTaskDisplayed() {
        val task = getDB().first()
        allOf(titleTask, withText(task.title)).isDisplayed()
        allOf(descriptionTask, withText(task.description)).isDisplayed()
        assertCheckBox(task.isCompleted)
    }




    private fun getDB(): List<Task> {
        val task = runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .getTasks()
        }
        return (task as Result.Success).data
    }

    private fun assertCheckBox(isCompleted: Boolean) {
        if (isCompleted)
            checkBox.isChecked()
        else
            checkBox.isNotChecked()
    }

    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }


}