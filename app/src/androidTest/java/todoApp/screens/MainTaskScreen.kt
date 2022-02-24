package todoApp.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.custom.espresso.action.getText
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.ServiceLocator.provideTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import todoApp.framework.FragDisplayed
import todoApp.framework.TasksData.countTasks

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

    fun openTaskDetails() {
        assertTaskDisplayed()
        recyclerView.lastItem().click()
    }

    fun assertTaskDisplayed() {
        val titleTask = recyclerView.lastItem(true).getChild(taskTitle).getText()
        val task = runBlocking {
            ServiceLocator.provideTasksRepository()
                .getTask(titleTask)
        }.toString()
        if("true" in task) {
            getTaskItem(titleTask).getChild(completeCheckBox).isChecked()
        }else{
            getTaskItem(titleTask).getChild(completeCheckBox).isNotChecked()
        }
    }

    fun addTasks(){
        val task = runBlocking {
            DefaultTasksRepository(TasksRemoteDataSource,provideTasksRepository()
        }
    }

    fun assertFilterTasks(CompleteTasks: Boolean) {
        if (CompleteTasks) {
            recyclerView.firstItem(true).getChild(completeCheckBox).isChecked()
            recyclerView.lastItem(true).getChild(completeCheckBox).isChecked()
        } else {
            recyclerView.firstItem(true).getChild(completeCheckBox).isNotChecked()
            recyclerView.lastItem(true).getChild(completeCheckBox).isNotChecked()
        }
    }

    fun assertFilterAllTasks() {
        recyclerView.item(countTasks,true).isDisplayed()
    }

    private fun getTaskItem(title:String): UltronRecyclerViewItem {
        val matcher = allOf(
            hasDescendant(
                allOf(taskTitle, withText(title))
            )
        )
        return recyclerView.item(matcher)
    }


    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }
}