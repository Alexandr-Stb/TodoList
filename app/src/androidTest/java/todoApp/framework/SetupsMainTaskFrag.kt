package todoApp.framework

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import todoApp.framework.TasksData.countTasks
import todoApp.screens.MainTaskScreen
import kotlin.random.Random

object SetupsMainTaskFrag {
    private val activityRule = ActivityTestRule(TasksActivity::class.java)
    private val filterBtn = withId(R.id.menu_filter)
    private val filterTitle = withId(R.id.title)
    private val filterAllTasksBtn = allOf(
        filterTitle, withText(R.string.nav_all)
    )
    private val filterActiveTasksBtn = allOf(
        filterTitle, withText(R.string.nav_active)
    )
    private val filterCompleteTasksBtn = allOf(
        filterTitle, withText(R.string.nav_completed)
    )
    private val clearCompletedBtn = allOf(
        withId(R.id.title), withText(R.string.menu_clear)
    )
    private val addTaskBtn = withId(R.id.add_task_fab)
    private val addTaskFragTitle = withId(R.id.add_task_description_edit_text)
    private val addTaskFragDescription = withId(R.id.add_task_title_edit_text)
    private val saveTaskBtn = withId(R.id.save_task_fab)
    private val menuBtn = withContentDescription("More options")
    private val recyclerView = withRecyclerView(R.id.tasks_list)
    private val completeCheckBox = withId(R.id.complete_checkbox)

    fun deleteAllTask() {
        runActivity()
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .deleteAllTasks()
        }
    }

    fun createTask(task: Task) {
        runActivity()
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .saveTask(task)
        }
    }

    fun createManyTask() {
        runActivity()
        for (i in 0..countTasks) {
            if (i % 2 == 0) {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TasksData.createTask(i, true))
                }
            } else {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TasksData.createTask(i, false))
                }
            }
        }

    }

    fun filterActiveTasks() {
        runActivity()
        filterBtn.click()
        if (filterActiveTasksBtn.isSuccess { withTimeout(1).isDisplayed() })
            filterActiveTasksBtn.click()
        else {
            filterBtn.click()
            filterActiveTasksBtn.click()
        }
    }

    fun filterCompleteTasks() {
        runActivity()
        filterBtn.click()
        if (filterCompleteTasksBtn.isSuccess { withTimeout(500).isDisplayed() })
            filterCompleteTasksBtn.click()
        else {
            filterBtn.click()
            filterCompleteTasksBtn.click()
        }
    }

    fun filterAllTasks() {
        runActivity()
        filterBtn.click()
        if (filterAllTasksBtn.isSuccess { withTimeout(1).isDisplayed() })
            filterAllTasksBtn.click()
        else {
            filterBtn.click()
            filterAllTasksBtn.click()
        }
    }

    fun addTask() {
        runActivity()
        addTaskBtn.click()
        addTaskFragTitle.replaceText("${Random.nextInt(12)}")
        addTaskFragDescription.replaceText("${Random.nextInt(12)}")
        saveTaskBtn.click()
    }

    fun clearCompleted() {
        runActivity()
        menuBtn.click()
        clearCompletedBtn.click()
    }

    fun taskStateChange(){
        runActivity()
        recyclerView.firstItem().getChild(completeCheckBox).click()
    }

    private fun runActivity() {
        activityRule.runOnUiThread {
            runBlocking {
                TasksRemoteDataSource.refreshTasks()
            }
        }
    }


}