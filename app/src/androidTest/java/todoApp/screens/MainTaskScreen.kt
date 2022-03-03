package todoApp.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.custom.espresso.action.getText
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator.provideTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking
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

    fun openTaskDetails() {
        assertTasksDisplayed()
        recyclerView.lastItem().click()
    }


    fun assertTasksDisplayed() {
        val firstTask = getDB().first()
        val lastTask = getDB().last()

        assertTaskAndCheckBox(firstTask)
        assertTaskAndCheckBox(lastTask)
    }


    fun assertFilterTasks(completeTasks: Boolean) {
        getDB().forEach {
            if (it.isCompleted == completeTasks)
                getTaskItem(it.title).isDisplayed()
        }
    }

    fun compareTaskInUIAndDB() {
        if (!assertTaskInDB())
            getTaskItem("this task was not found in the database")
        else
            getTaskItem(recyclerView.lastItem(true).getChild(taskTitle).getText())
    }

    fun assertClearCompletedTasks(){
        getDB().forEach {
            getTaskItem(it.title)
                .getChild(completeCheckBox).isNotChecked()
        }
    }

    fun assertTaskStateChange(){
        val firstTaskDB = getDB().first()
        val firstTask = getTaskItem(firstTaskDB.title)
        firstTask.getChild(completeCheckBox).click()
        if(firstTask.getChild(completeCheckBox).isSuccess { withTimeout(1000).isChecked() }){
            firstTask.getChild(completeCheckBox).click()
        }
        if(firstTaskDB.isCompleted)
            firstTask.getChild(completeCheckBox).isNotChecked()
        else
            firstTask.getChild(completeCheckBox).isChecked()
    }


    private fun assertTaskInDB(): Boolean {
        val title = recyclerView.lastItem().getChild(taskTitle).getText()
        return (title == getDB().last().title)
    }

    private fun getDB(): List<Task> {
        val task = runBlocking {
//            TasksRemoteDataSource.getTasks()
            provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .getTasks()
        }
        return (task as Result.Success).data
    }

    private fun getTaskItem(title: String): UltronRecyclerViewItem {
        val matcher = allOf(
            hasDescendant(
                allOf(taskTitle, withText(title))
            )
        )
        return recyclerView.item(matcher)
    }

    private fun assertTaskAndCheckBox(task:Task) {
        if (task.isCompleted)
            getTaskItem(task.title).isDisplayed()
                .getChild(completeCheckBox).isChecked()
        else
            getTaskItem(task.title).isDisplayed()
                .getChild(completeCheckBox).isNotChecked()
    }


    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }
}